package com.winetime.winetime.tastingnotes

import com.winetime.winetime.users.User
import com.winetime.winetime.wines.Wine
import javax.persistence.*

@Entity
@Table(name = "tasting_notes")
data class TastingNote(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val notes: String = "",
        @Enumerated(EnumType.ORDINAL)
        val score: TastingScore = TastingScore.ONE,
        @ManyToOne
        @JoinColumn(name = "USER_ID")
        var user: User = User(),
        @ManyToOne
        @JoinColumn(name = "WINE_ID")
        var wine: Wine = Wine()
)

enum class TastingScore(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
}