package com.winetime.winetime.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersController(
        @Autowired private val userTokenService: UserTokenService,
        @Autowired private val userRepository: UserRepository
) {

    @PostMapping("/token")
    fun getToken(@RequestBody userCredentials: User) : ResponseEntity<UserTokenResponse> {
        val token = userTokenService.generate(userCredentials)
        val status = if (token != null)  HttpStatus.OK else HttpStatus.BAD_REQUEST

        return ResponseEntity(UserTokenResponse(token), status)
    }

    @PostMapping
    fun create(@RequestBody userAttributes: User): ResponseEntity<User> {
        val user = userRepository.save(userAttributes)
        return ResponseEntity(user, HttpStatus.CREATED)
    }
}