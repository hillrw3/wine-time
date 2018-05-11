package com.winetime.winetime.users

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun generateToken(userCredentials: User) : String? {
        val user = userRepository.findFirstByUsernameAndPassword(
                username = userCredentials.username,
                password = userCredentials.password
        )

        return if (user != null) "tokenTime" else null
    }

    fun save(userAttributes: User): User {
        return userRepository.save(userAttributes)
    }
}