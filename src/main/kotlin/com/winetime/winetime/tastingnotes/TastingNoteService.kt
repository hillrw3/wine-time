package com.winetime.winetime.tastingnotes

import com.winetime.winetime.users.UserRepository
import com.winetime.winetime.wines.WineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TastingNoteService(
        @Autowired val tastingNoteRepository: TastingNoteRepository,
        @Autowired val userRepository: UserRepository,
        @Autowired val wineRepository: WineRepository
) {

    fun save(tastingNoteTemplate: TastingNoteCreationTemplate): TastingNote {
        val user = userRepository.getOne(tastingNoteTemplate.userId!!)
        val wine = wineRepository.getOne(tastingNoteTemplate.wineId!!)
        return tastingNoteRepository.save(
                TastingNote(
                        user = user,
                        wine = wine,
                        notes = tastingNoteTemplate.notes!!,
                        score = tastingNoteTemplate.score!!
                )
        )
    }

    fun findAll(): List<TastingNote> {
        return tastingNoteRepository.findAll()
    }
}