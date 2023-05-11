package com.example.cinema.repositories

import com.example.cinema.models.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository: JpaRepository<Client, Int> {
    fun findByEmail(email:String): Client?
}