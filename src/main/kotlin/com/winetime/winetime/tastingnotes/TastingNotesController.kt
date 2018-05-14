package com.winetime.winetime.tastingnotes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasting-notes")
class TastingNotesController(@Autowired val tastingNoteService: TastingNoteService) {
    @GetMapping
    fun getNotes() : TastingNotesResponse = TastingNotesResponse(tastingNoteService.findAll())

    @PostMapping
    fun create(@RequestBody tastingNoteTemplate: TastingNoteCreationTemplate): ResponseEntity<TastingNoteResponse> {
        if (tastingNoteTemplate.errors.isNotEmpty()) {
            return ResponseEntity(
                    TastingNoteResponse(errors = tastingNoteTemplate.errors),
                    HttpStatus.BAD_REQUEST
            )
        }

        val tastingNote = tastingNoteService.save(tastingNoteTemplate)
        return ResponseEntity(TastingNoteResponse(tastingNote), HttpStatus.CREATED)
    }
}