package com.winetime.winetime.acceptance

import com.winetime.winetime.createHeaders
import com.winetime.winetime.createWine
import com.winetime.winetime.wines.Wine
import com.winetime.winetime.wines.WineRepository
import com.winetime.winetime.wines.WineResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus

class WinesControllerTest : BaseAcceptanceTest() {
    @Autowired
    lateinit var wineRepo: WineRepository

    @DisplayName("GET /wines")
    @Nested
    inner class GetWines {
        @DisplayName("returns wines from the database")
        @Test
        fun returns200() {
            val wine1 = Wine(
                    winery = "blah",
                    varietal = "barolo",
                    vintage = 2018,
                    name = "coolio",
                    country = "Italy",
                    region = "Piemonte"
            )
            val wine2 = Wine(
                    winery = "small artisanal vineyard",
                    varietal = "chardonnay",
                    vintage = 2015,
                    name = "Butter n jam",
                    country = "USA",
                    region = "California"
            )

            wineRepo.saveAll(mutableListOf(wine1, wine2))

            val response = restTemplate.getForEntity<WineResponse>("/wines")

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body?.wines).contains(wine1, wine2)
        }
    }

    @DisplayName("POST /wines")
    @Nested
    inner class PostWines {
        @DisplayName("creates a new wine")
        @Test
        fun success() {
            assertThat(wineRepo.findAll()).isEmpty()
            val wine = createWine()
            val wineWithId = wine.copy(id=1)
            val request = HttpEntity(mapper.writeValueAsString(wine), createHeaders())

            val response = restTemplate.postForEntity("/wines", request, Wine::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
            assertThat(response.body).isEqualTo(wineWithId)
            assertThat(wineRepo.findAll()).contains(wineWithId)
        }

        @DisplayName("returns an error if the request is malformed")
        @Test
        fun failure() {
            val request = HttpEntity("'some': 'gibberish'", createHeaders())

            val response = restTemplate.postForEntity("/wines", request, Wine::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        }
    }
}