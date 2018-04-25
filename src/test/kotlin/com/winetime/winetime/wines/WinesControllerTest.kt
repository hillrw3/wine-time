package com.winetime.winetime.wines

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WinesControllerTest {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var wineRepo: WineRepository

    @Autowired
    lateinit var flyway: Flyway

    @AfterEach
    fun tearDown() {
        flyway.clean()
        flyway.migrate()
    }

    @DisplayName("GET /wines")
    @Nested()
    inner class GetWines {

        @DisplayName("it returns a 200")
        @Test
        fun returns200() {
            val wine = wineRepo.save(Wine(winery = "blah2", varietal = "baroloz", vintage = 2018, name = "coolio"))

            println("wine = ${wine}")
            val response = restTemplate.getForEntity<List<Wine>>("/wines")

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

            val mapper = jacksonObjectMapper()
            val wineFromDb = mapper.convertValue(response.body[0], Wine::class.java)
            assertThat(wineFromDb.winery).isEqualTo(wine.winery)
            assertThat(wineFromDb.varietal).isEqualTo(wine.varietal)
            assertThat(wineFromDb.vintage).isEqualTo(wine.vintage)
            assertThat(wineFromDb.name).isEqualTo(wine.name)
        }
    }

}