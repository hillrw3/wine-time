package com.winetime.winetime.unit.users

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.winetime.winetime.tastingnotes.TastingNoteRepository
import com.winetime.winetime.users.User
import com.winetime.winetime.users.UserRepository
import com.winetime.winetime.users.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class UserServiceTest {
    lateinit var service: UserService
    val userRepo = mock<UserRepository>()

    val tastingNoteRepo = mock<TastingNoteRepository>()

    @BeforeEach
    fun setup() {
        service = UserService(userRepo, tastingNoteRepo)
    }

    @DisplayName("#generateToken")
    @Nested
    inner class GetToken {
        @DisplayName("returns a token if user exists with passed credentials")
        @Test
        fun success() {
            val user = User(username = "bill", password = "superSecure")
            whenever(userRepo.findFirstByUsernameAndPassword(any(), any())).thenReturn(user)

            val token = service.generateToken(user)

            Assertions.assertThat(token).isEqualTo("tokenTime")
        }

        @DisplayName("returns an unsuccessful response and no token")
        @Test
        fun failure() {
            whenever(userRepo.findFirstByUsernameAndPassword(any(), any())).thenReturn(null)

            val token = service.generateToken(User(username = "bill", password = "badPassword"))

            Assertions.assertThat(token).isNull()
        }
    }
}