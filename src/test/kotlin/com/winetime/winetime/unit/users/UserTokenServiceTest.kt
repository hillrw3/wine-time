package com.winetime.winetime.unit.users

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.winetime.winetime.users.User
import com.winetime.winetime.users.UserRepository
import com.winetime.winetime.users.UserTokenService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class UserTokenServiceTest {
    lateinit var service: UserTokenService
    val userRepo = mock<UserRepository> ()

    @BeforeEach
    fun setup() {
        service = UserTokenService(userRepo)
    }

    @DisplayName("#generate")
    @Nested
    inner class GetToken {
        @DisplayName("returns a token if user exists with passed credentials")
        @Test
        fun success() {
            val user = User(username = "bill", password = "superSecure")
            whenever(userRepo.findFirstByUsernameAndPassword(any(), any())).thenReturn(user)

            val token = service.generate(user)

            Assertions.assertThat(token).isEqualTo("tokenTime")
        }

        @DisplayName("returns an unsuccessful response and no token")
        @Test
        fun failure() {
            whenever(userRepo.findFirstByUsernameAndPassword(any(), any())).thenReturn(null)

            val token = service.generate(User(username = "bill", password = "badPassword"))

            Assertions.assertThat(token).isNull()
        }
    }
}