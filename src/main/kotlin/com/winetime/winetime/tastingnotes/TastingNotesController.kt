package com.winetime.winetime.tastingnotes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasting-notes")
class TastingNotesController(
        @Autowired val tastingNoteRepository: TastingNoteRepository,
        @Autowired val tastingNoteService: TastingNoteService
) {
    @GetMapping
    fun getNotes() : TastingNotesResponse = TastingNotesResponse(tastingNoteRepository.findAll())

    @PostMapping
    fun create(@RequestBody tastingNoteTemplate: TastingNoteCreationTemplate): ResponseEntity<TastingNote> {
        val tastingNote = tastingNoteService.save(tastingNoteTemplate)
        return ResponseEntity(tastingNote, HttpStatus.CREATED)
    }
}