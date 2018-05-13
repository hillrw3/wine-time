package com.winetime.winetime

import com.winetime.winetime.tastingnotes.TastingNote
import com.winetime.winetime.tastingnotes.TastingScore
import com.winetime.winetime.users.User
import com.winetime.winetime.wines.Wine
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

fun createWine(
        winery: String = "Castelgreve",
        varietal: String = "Chianti Classico",
        vintage: Int = 2013,
        name: String = "Riserva",
        country: String = "Italy",
        region: String = "Tuscany"
): Wine = Wine(winery = winery, varietal = varietal, vintage = vintage, name = name, country = country, region = region)

fun createUser(
        username: String = "bill",
        password: String = "superSecure"
): User = User(username = username, password = password)

fun createTastingNote(
        user: User,
        wine: Wine,
        notes: String = "Dry with a hint of Tabasco",
        score: TastingScore = TastingScore.FOUR
) : TastingNote = TastingNote(user = user, wine = wine, notes = notes, score = score)

fun createHeaders() : HttpHeaders {
    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_JSON
    return headers
}