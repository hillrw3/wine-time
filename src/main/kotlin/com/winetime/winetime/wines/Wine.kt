package com.winetime.winetime.wines

import javax.persistence.*

@Entity
@Table(name = "wines")
data class Wine(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val winery: String = "",
        val name: String = "",
        val varietal: String = "",
        val vintage: Int = 0
)