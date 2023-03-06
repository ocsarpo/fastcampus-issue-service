package com.fastcampus.userservice.controller

import com.fastcampus.userservice.model.AuthToken
import com.fastcampus.userservice.model.SignInRequest
import com.fastcampus.userservice.model.SignInResponse
import com.fastcampus.userservice.model.SignUpRequest
import com.fastcampus.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/signup")
    suspend fun signUp(
        @RequestBody request: SignUpRequest,
    ) {
        userService.signUp(request)
    }

    @PostMapping("/signin")
    suspend fun sighIn(
        @RequestBody signInRequest: SignInRequest,
    ): SignInResponse {
        return userService.signIn(signInRequest)
    }

    @DeleteMapping("/signout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun signOut(
        @AuthToken token: String
    ) {
        userService.signOut(token)
    }
}
