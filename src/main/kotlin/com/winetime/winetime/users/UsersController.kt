package com.winetime.winetime.users

import com.winetime.winetime.tastingnotes.TastingNotesResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(@Autowired private val userService: UserService) {

    @GetMapping("/{id}/tasting-notes")
    fun getTastingNotes(@PathVariable(name = "id") id: Int): TastingNotesResponse {
        val tastingNotes = userService.getTastingNotes(id)

        return TastingNotesResponse(tastingNotes)
    }

    @PostMapping("/token")
    fun getToken(@RequestBody userCredentials: User): ResponseEntity<UserTokenResponse> {
        val token = userService.generateToken(userCredentials)
        val status = if (token != null) HttpStatus.OK else HttpStatus.BAD_REQUEST

        return ResponseEntity(UserTokenResponse(token), status)
    }

    @PostMapping
    fun create(@RequestBody userAttributes: User): ResponseEntity<User> {
        val user = userService.save(userAttributes)

        return ResponseEntity(user, HttpStatus.CREATED)
    }
}