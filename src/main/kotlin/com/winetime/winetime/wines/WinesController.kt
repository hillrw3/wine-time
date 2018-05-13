package com.winetime.winetime.wines

import com.winetime.winetime.tastingnotes.TastingNotesResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wines")
class WinesController(@Autowired private val wineService: WineService) {

    @GetMapping
    fun getWines() = WineResponse(wines = wineService.findAll())

    @GetMapping("/{id}/tasting-notes")
    fun getWineTastingNotes(@PathVariable(name = "id") id: Int): TastingNotesResponse {
        val tastingNotes = wineService.getTastingNotes(id)

        return TastingNotesResponse(tastingNotes)
    }

    @PostMapping
    fun create(@RequestBody wineAttributes : Wine) : ResponseEntity<Wine> {
        val wine = wineService.save(wineAttributes)

        return ResponseEntity(wine, HttpStatus.CREATED)
    }
}