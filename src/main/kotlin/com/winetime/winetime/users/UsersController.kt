package com.winetime.winetime.users

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersController(private val userTokenService: UserTokenService) {

    @PostMapping("/token")
    fun getToken(@RequestBody userCredentials: User) : ResponseEntity<UserTokenResponse> {
        val token = userTokenService.generate(userCredentials)
        val status = if (token != null)  HttpStatus.OK else HttpStatus.BAD_REQUEST

        return ResponseEntity(UserTokenResponse(token), status)
    }
}