package com.winetime.winetime.wines

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WineRepository : JpaRepository<Wine, Int>