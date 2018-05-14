package com.winetime.winetime.tastingnotes

data class TastingNoteCreationTemplate(
        val notes: String? = null,
        val score: TastingScore? = null,
        val userId: Int? = null,
        val wineId: Int? = null
) {
    val errors: Map<String, String> = calculateErrors()

    private fun requiredFields() : Map<String, Any?> = mapOf(
            "notes" to notes,
            "score" to score,
            "userId" to userId,
            "wineId" to wineId
    )

    private fun calculateErrors(): Map<String, String> {
        val errorMap = mutableMapOf<String, String>()
        requiredFields().forEach {
            if (it.value == null) {
                errorMap[it.key] = "is a required field"
            }
        }

        return errorMap
    }
}