package com.winetime.winetime.wines

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WinesController(private val wineRepository: WineRepository) {

    @GetMapping("/wines")
    fun getWines() = WineResponse(wines = wineRepository.findAll())
}