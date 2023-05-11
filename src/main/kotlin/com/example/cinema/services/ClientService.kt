package com.example.cinema.services

import com.example.cinema.models.Client
import com.example.cinema.repositories.ClientRepository
import org.springframework.stereotype.Service

@Service
class ClientService(private val clientRepository: ClientRepository) {

    fun save(client: Client): Client{
        return this.clientRepository.save(client)
    }

    fun findByEmail(email: String): Client? {
        return this.clientRepository.findByEmail(email)
    }
}