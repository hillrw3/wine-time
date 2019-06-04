package com.winetime.winetime.unit.tastingnotes

import com.winetime.winetime.tastingnotes.TastingNoteRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TastingNoteRequestTest {
    @DisplayName("validates required fields on object creation")
    @Test
    fun validation() {
        val tastingNoteCreationTemplate = TastingNoteRequest()

        assertThat(tastingNoteCreationTemplate.errors).isEqualTo(
                mapOf(
                        "notes" to "is a required field",
                        "score" to "is a required field",
                        "userId" to "is a required field",
                        "wineId" to "is a required field"
                )
        )

    }
}