package com.fastcampus.userservice.service

import com.fastcampus.userservice.config.JWTProperties
import com.fastcampus.userservice.domain.entity.UserEntity
import com.fastcampus.userservice.domain.repository.UserRepository
import com.fastcampus.userservice.exception.PasswordNotMatchedException
import com.fastcampus.userservice.exception.UserExistsException
import com.fastcampus.userservice.exception.UserNotFoundException
import com.fastcampus.userservice.model.SignInRequest
import com.fastcampus.userservice.model.SignInResponse
import com.fastcampus.userservice.model.SignUpRequest
import com.fastcampus.userservice.utils.BCryptUtils
import com.fastcampus.userservice.utils.JWTClaim
import com.fastcampus.userservice.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<UserEntity>,
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }

            val user = UserEntity(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )

            userRepository.save(user)
        }
    }

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(signInRequest.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(signInRequest.password, password)

            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username,
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = jwtClaim.email,
                username = jwtClaim.username,
                token = token,
            )
        }
    }

    suspend fun signOut(token: String) {
        cacheManager.awaitEvict(key = token)
    }
}
