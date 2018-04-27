package com.winetime.winetime.users

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findFirstByUsernameAndPassword(username: String, password: String) : User?
}