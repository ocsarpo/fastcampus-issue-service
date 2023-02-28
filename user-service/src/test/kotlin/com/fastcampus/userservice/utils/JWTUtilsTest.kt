package com.fastcampus.userservice.utils

import com.fastcampus.userservice.config.JWTProperties
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class JWTUtilsTest {

    private val logger = KotlinLogging.logger { }

    @Test
    fun createTokenTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail",
            profileUrl = "profile.jpg",
            username = "개발자",
        )

        val properties = JWTProperties(
            issuer = "fake-jira",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtils.createToken(jwtClaim, properties)
        assertNotNull(token)

        logger.info { "token : $token" }
    }

    @Test
    fun decodeTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "dev@gmail",
            profileUrl = "profile.jpg",
            username = "개발자",
        )

        val properties = JWTProperties(
            issuer = "fake-jira",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JWTUtils.createToken(jwtClaim, properties)

        val decodedJWT = JWTUtils.decode(token, secret = properties.secret, issuer = properties.issuer)

        with(decodedJWT) {
            logger.info { "claims : $claims " }

            val userId = this.claims["userId"]!!.asLong()
            assertEquals(userId, jwtClaim.userId)

            val email = this.claims["email"]!!.asString()
            assertEquals(email, jwtClaim.email)

            val profileUrl = this.claims["profileUrl"]!!.asString()
            assertEquals(profileUrl, jwtClaim.profileUrl)

            val username = this.claims["username"]!!.asString()
            assertEquals(username, jwtClaim.username)
        }
    }
}
