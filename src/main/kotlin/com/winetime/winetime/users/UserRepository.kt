package com.winetime.winetime.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findFirstByUsernameAndPassword(username: String, password: String) : User?
}