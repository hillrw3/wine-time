package com.winetime.winetime.wines

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "wines")
data class Wine(
        @Id
        val id: Int? = 0,
        val winery: String = "",
        val name: String = "",
        val varietal: String = "",
        val vintage: Int = 0
)