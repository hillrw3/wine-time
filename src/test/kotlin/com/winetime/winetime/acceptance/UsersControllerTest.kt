package com.winetime.winetime.acceptance

import com.winetime.winetime.users.User
import com.winetime.winetime.users.UserRepository
import com.winetime.winetime.users.UserTokenResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class UsersControllerTest : BaseAcceptanceTest() {

    @Autowired
    lateinit var userRepository: UserRepository

    @DisplayName("POST /users/token")
    @Nested
    inner class PostToken {
        @DisplayName("returns a token when passed correct user credentials")
        @Test
        fun success() {
            val user = User(username = "bill", password = "superSecure")
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
}