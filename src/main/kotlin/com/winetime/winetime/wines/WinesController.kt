package com.winetime.winetime.wines

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wines")
class WinesController(private val wineRepository: WineRepository) {

    @GetMapping
    fun getWines() = WineResponse(wines = wineRepository.findAll())

    @PostMapping
    fun create(@RequestBody wine : Wine) : ResponseEntity<Wine> {
        val createdWine = wineRepository.save(wine)
        return ResponseEntity(createdWine, HttpStatus.CREATED)
    }
}