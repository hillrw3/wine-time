package com.winetime.winetime.wines

import com.winetime.winetime.tastingnotes.TastingNote
import com.winetime.winetime.tastingnotes.TastingNoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WineService(
        @Autowired private val wineRepository: WineRepository,
        @Autowired private val tastingNoteRepository: TastingNoteRepository
) {
    fun findAll(): List<Wine> {
        return wineRepository.findAll()
    }

    fun save(wineAttributes: Wine): Wine {
        return wineRepository.save(wineAttributes)
    }

    fun getTastingNotes(wineId: Int): List<TastingNote> {
        return tastingNoteRepository.findByWineId(wineId)
    }
}