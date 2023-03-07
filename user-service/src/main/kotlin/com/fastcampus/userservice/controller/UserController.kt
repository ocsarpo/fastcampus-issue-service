package com.fastcampus.userservice.controller

import com.fastcampus.userservice.model.*
import com.fastcampus.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/me")
    suspend fun get(
        @AuthToken token: String,
    ): MeResponse = MeResponse(userService.getByToken(token))
}
