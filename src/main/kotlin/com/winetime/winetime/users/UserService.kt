package com.winetime.winetime.users

import com.winetime.winetime.tastingnotes.TastingNote
import com.winetime.winetime.tastingnotes.TastingNoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
        @Autowired private val userRepository: UserRepository,
        @Autowired private val tastingNoteRepository: TastingNoteRepository
) {
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

    fun getTastingNotes(id: Int) : List<TastingNote> {
        return tastingNoteRepository.findByUserId(id)
    }
}