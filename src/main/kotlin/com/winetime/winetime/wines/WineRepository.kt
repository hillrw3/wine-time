package com.winetime.winetime.wines

import org.springframework.data.jpa.repository.JpaRepository

interface WineRepository : JpaRepository<Wine, Int>