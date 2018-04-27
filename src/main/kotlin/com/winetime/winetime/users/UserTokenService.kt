package com.winetime.winetime.users

import org.springframework.stereotype.Service

@Service
class UserTokenService(private val userRepository: UserRepository) {
    fun generate(userCredentials: User) : String? {
        val user = userRepository.findFirstByUsernameAndPassword(
                username = userCredentials.username,
                password = userCredentials.password
        )

        return if (user != null) "tokenTime" else null
    }
}