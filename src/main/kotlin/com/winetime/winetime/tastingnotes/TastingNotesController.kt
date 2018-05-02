package com.winetime.winetime.tastingnotes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TastingNotesController(@Autowired val tastingNoteRepository: TastingNoteRepository) {
    @GetMapping("/tasting-notes")
    fun getNotes() : TastingNotesResponse = TastingNotesResponse(tastingNoteRepository.findAll())
}