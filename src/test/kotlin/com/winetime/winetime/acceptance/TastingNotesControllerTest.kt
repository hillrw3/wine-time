package com.winetime.winetime.acceptance

import com.winetime.winetime.createUser
import com.winetime.winetime.createWine
import com.winetime.winetime.tastingnotes.*
import com.winetime.winetime.users.UserRepository
import com.winetime.winetime.wines.WineRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

internal class TastingNotesControllerTest : BaseAcceptanceTest() {
    @Autowired
    lateinit var userRepo: UserRepository

    @Autowired
    lateinit var wineRepo: WineRepository

    @Autowired
    lateinit var tastingNoteRepo: TastingNoteRepository

    @DisplayName("GET /tasting-notes")
    @Nested
    inner class GetNotes {
        @DisplayName("returns all tasting notes")
        @Test
        fun allNotes() {
            val user1 = createUser(username = "jim")
            val user2 = createUser(username = "billy")
            val wine = createWine()
            userRepo.saveAll(mutableListOf(user1, user2))
            wineRepo.save(wine)
            val tastingNote1 = TastingNote(
                    wine = wine,
                    user = user1,
                    notes = "Fruity and delightful",
                    score = TastingScore.FOUR
            )

            val tastingNote2 = TastingNote(
                    wine = wine,
                    user = user2,
                    notes = "Like a fruit-filled dirty bomb went off in my mouth",
                    score = TastingScore.TWO
            )
            tastingNoteRepo.saveAll(mutableListOf(tastingNote1, tastingNote2))

            val response = restTemplate.getForEntity("/tasting-notes", TastingNotesResponse::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body?.tastingNotes).contains(tastingNote1, tastingNote2)
        }
    }

    @DisplayName("POST /tasting-notes")
    @Nested
    inner class PostNotes {
        val user = userRepo.save(createUser())
        val wine = wineRepo.save(createWine())

        @DisplayName("creates a new tasting new from the user and wine")
        @Test
        fun success() {
            assertThat(tastingNoteRepo.findAll()).isEmpty()
            val tastingNote = TastingNote(
                    wine = wine,
                    user = user,
                    notes = "Fruity and delightful",
                    score = TastingScore.FOUR
            )
            val tastingNoteWithId = tastingNote.copy(id = 1)

            val requestBody = TastingNoteRequest(
                    wineId = wine.id!!,
                    userId = user.id!!,
                    notes = tastingNote.notes,
                    score = tastingNote.score
            )

            val response = restTemplate.postForEntity("/tasting-notes", requestBody, TastingNoteResponse::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
            assertThat(response.body?.tastingNote).isEqualTo(tastingNoteWithId)
            assertThat(tastingNoteRepo.findAll()).contains(tastingNoteWithId)
        }

        @DisplayName("validates all required fields are present")
        @Test
        fun validation() {
            val requestBody = TastingNoteRequest(
                    wineId = wine.id,
                    userId = user.id,
                    notes = "delightful"
            )

            val response = restTemplate.postForEntity("/tasting-notes", requestBody, TastingNoteResponse::class.java)

            assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            assertThat(response.body?.errors).containsKey("score")
        }
    }
}