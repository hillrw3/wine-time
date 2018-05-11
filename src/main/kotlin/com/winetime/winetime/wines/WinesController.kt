package com.winetime.winetime.wines

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wines")
class WinesController(@Autowired private val wineService: WineService) {

    @GetMapping
    fun getWines() = WineResponse(wines = wineService.findAll())

    @PostMapping
    fun create(@RequestBody wineAttributes : Wine) : ResponseEntity<Wine> {
        val wine = wineService.save(wineAttributes)
        return ResponseEntity(wine, HttpStatus.CREATED)
    }
}