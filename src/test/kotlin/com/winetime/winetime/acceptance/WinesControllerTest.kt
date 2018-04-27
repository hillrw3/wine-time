package com.winetime.winetime.acceptance

import com.winetime.winetime.wines.Wine
import com.winetime.winetime.wines.WineRepository
import com.winetime.winetime.wines.WineResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WinesControllerTest : BaseAcceptanceTest() {
    @Autowired
    lateinit var wineRepo: WineRepository

    @DisplayName("GET /wines")
    @Nested()
    inner class GetWines {
        @DisplayName("returns wines from the database")
        @Test
        fun returns200() {
            val wine1 = wineRepo.save(Wine(
                    winery = "blah",
                    varietal = "barolo",
                    vintage = 2018,
                    name = "coolio"
            ))
            val wine2 = wineRepo.save(Wine(
                    winery = "small artisanal vineyard",
                    varietal = "chardonnay",
                    vintage = 2015,
                    name = "Butter n jam"
            ))

            val response = restTemplate.getForEntity<WineResponse>("/wines")

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body?.wines).contains(wine1, wine2)
        }
    }
}