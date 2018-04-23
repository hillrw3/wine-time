package com.winetime.winetime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class WineTimeApplication

fun main(args: Array<String>) {
    runApplication<WineTimeApplication>(*args)
}
