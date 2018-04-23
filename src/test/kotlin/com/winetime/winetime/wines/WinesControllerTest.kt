package com.winetime.winetime.wines

import org.assertj.core.api.Assertions.assertThat
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

    @DisplayName("GET /wines")
    @Nested()
    inner class GetWines {

        @DisplayName("it returns a 200")
        @Test
        fun returns200() {
            wineRepo.save(Wine(winery = "blah2", varietal = "baroloz", vintage = 2018, name = "coolio"))

            val response = restTemplate.getForEntity<List<Wine>>("/wines")

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).isNotEmpty
        }
    }

}