package com.winetime.winetime.tastingnotes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TastingNoteRepository : JpaRepository<TastingNote, Int>