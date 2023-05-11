package com.example.cinema

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional


@Repository
interface SeatRepository : JpaRepository<Seat, Long> {
    override fun findById(id: Long): Optional<Seat>
    override fun deleteById(id: Long)
    override fun findAll(): List<Seat>
    fun save(seat: Seat): Seat
}



