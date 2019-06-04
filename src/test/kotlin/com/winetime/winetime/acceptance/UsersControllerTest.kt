package com.winetime.winetime.acceptance

import com.winetime.winetime.createHeaders
import com.winetime.winetime.createTastingNote
import com.winetime.winetime.createUser
import com.winetime.winetime.createWine
import com.winetime.winetime.tastingnotes.TastingNoteRepository
import com.winetime.winetime.tastingnotes.TastingNotesResponse
import com.winetime.winetime.users.User
import com.winetime.winetime.users.UserRepository
import com.winetime.winetime.users.UserTokenResponse
import com.winetime.winetime.wines.WineRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus

internal class UsersControllerTest : BaseAcceptanceTest() {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var tastingNoteRepository: TastingNoteRepository

    @Autowired
    lateinit var wineRepository: WineRepository

    @DisplayName("POST /users/token")
    @Nested
    inner class PostToken {
        @DisplayName("returns a token when passed correct user credentials")
        @Test
        fun success() {
            val user = createUser()
            userRepository.save(user)

            val response = restTemplate.postForEntity("/users/token", user, UserTokenResponse::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body?.token).isNotNull()
        }

        @DisplayName("returns no token and a bad request status when passed incorrect user credentials")
        @Test
        fun failure() {
            val user = User(username = "bill", password = "someRandomBadPassword")

            val response = restTemplate.postForEntity("/users/token", user, UserTokenResponse::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            assertThat(response.body?.token).isNull()
        }
    }

    @DisplayName("POST /users")
    @Nested
    inner class PostUsers {
        @DisplayName("creates a new user")
        @Test
        fun success() {
            assertThat(userRepository.findAll()).isEmpty()
            val user = createUser()
            val userWithId = user.copy(id = 1)

            val response = restTemplate.postForEntity("/users", user, User::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
            assertThat(response.body).isEqualTo(userWithId)
            assertThat(userRepository.findAll()).contains(userWithId)
        }

        @DisplayName("returns an error code if the request is malformed")
        @Test
        fun badRequest() {
            val request = HttpEntity("'some': 'badData'", createHeaders())
            val response = restTemplate.postForEntity("/users", request, User::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            assertThat(userRepository.findAll()).isEmpty()
        }
    }

    @DisplayName("GET /users/:id/tasting-notes")
    @Nested
    inner class GetUserWines {
        @DisplayName("returns the tasting notes associated to the user")
        @Test
        fun success() {
            val user = userRepository.save(createUser())
            val otherUser = userRepository.save(createUser())
            val wine = wineRepository.save(createWine())
            val tastingNote1 = createTastingNote(user = user, notes = "Delightful", wine = wine)
            val tastingNote2 = createTastingNote(user = user, notes = "Scrumptious", wine = wine)
            val notMyTastingNote = createTastingNote(user = otherUser, wine = wine)
            tastingNoteRepository.saveAll(listOf(tastingNote1, tastingNote2, notMyTastingNote))

            val response = restTemplate.getForEntity("/users/${user.id}/tasting-notes", TastingNotesResponse::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body?.tastingNotes).contains(tastingNote1, tastingNote2)
            assertThat(response.body?.tastingNotes).doesNotContain(notMyTastingNote)
        }

        @DisplayName("handles empty lists gracefully")
        @Test
        fun emptySuccess() {
            val user = userRepository.save(createUser())

            val response = restTemplate.getForEntity("/users/${user.id}/tasting-notes", TastingNotesResponse::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body?.tastingNotes).isEmpty()
        }
    }
}