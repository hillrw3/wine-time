package com.winetime.winetime.wines

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WineService(@Autowired val wineRepository: WineRepository) {
    fun findAll(): List<Wine> {
        return wineRepository.findAll()
    }

    fun save(wineAttributes: Wine): Wine {
        return wineRepository.save(wineAttributes)
    }
}