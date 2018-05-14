package com.winetime.winetime.tastingnotes

data class TastingNotesResponse(val tastingNotes: List<TastingNote> = emptyList())

data class TastingNoteResponse(
        val tastingNote: TastingNote? = null,
        val errors: Map<String, String> = emptyMap()
)