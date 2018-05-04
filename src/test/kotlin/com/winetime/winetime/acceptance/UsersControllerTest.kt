package com.winetime.winetime.acceptance

import com.winetime.winetime.createUser
import com.winetime.winetime.users.User
import com.winetime.winetime.users.UserRepository
import com.winetime.winetime.users.UserTokenResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

internal class UsersControllerTest : BaseAcceptanceTest() {
    @Autowired
    lateinit var userRepository: UserRepository

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
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity(user, headers)

            val response = restTemplate.postForEntity("/users", request, User::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
            assertThat(response.body).isEqualTo(userWithId)
            assertThat(userRepository.findAll()).contains(userWithId)
        }

        @DisplayName("returns an error code if the request is malformed")
        @Test
        fun badRequest() {
            assertThat(userRepository.findAll()).isEmpty()
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity("'some': 'badData'", headers)

            val response = restTemplate.postForEntity("/users", request, User::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        }
    }
}