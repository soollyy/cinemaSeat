package com.example.cinema

import com.example.cinema.models.Client
import com.example.cinema.repositories.ClientRepository
import jakarta.persistence.Table
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.http.ResponseEntity
//import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Entity
@Table(name = "cinemaSeat")
data class Seat ( //SECTION= SEAT LETTER/NUMBER EG. L5
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = -1,
    var section: String = "",
    var isAvailable: Boolean = true
)

val seats = listOf(
    Seat(id = 1, section = "A", isAvailable = true),
    Seat(id = 2, section = "A", isAvailable = false),
    Seat(id = 3, section = "B", isAvailable = true),
    Seat(id = 4, section = "B", isAvailable = false),
    Seat(id = 5, section = "C", isAvailable = true),
    Seat(id = 6, section = "C", isAvailable = true)
) //dummy data to see if postman was showing this dummy data before creating a db


@RestController
@RequestMapping("/seats")
class SeatController(
    private val seatRepository: SeatRepository,
) {

    @GetMapping
    fun getAvailableSeats(principal: Principal?): Any {

        if (principal == null) {
            val errorMessage = "You must register or login first"
            return ResponseEntity.badRequest().body(errorMessage)
        }

        val seats = seatRepository.findAll().filter { it.isAvailable }
        return ResponseEntity.ok(seats)
    } // returning all the available seats

    @GetMapping("/{id}")
    fun getAvailableSeatById(@PathVariable id: Long, principal: Principal?): ResponseEntity<Any?> {

        val seat = seatRepository.findById(id).orElse(null)

        if (principal == null) {
            val errorMessage = "You must register or login first"
            return ResponseEntity.badRequest().body(errorMessage)
        }

        return if (seat != null && seat.isAvailable) {
            ResponseEntity.ok(seat)
        } else {
            val errorMessage = "Seat already been booked"
           return ResponseEntity.badRequest().body(errorMessage)
        }
    } // returning available seat by Id


    @PostMapping("/{id}")
    fun bookSeat(@PathVariable id: Long, principal: Principal?): ResponseEntity<String> {

        val seat = seatRepository.findById(id).orElse(null)

        if (principal == null) {
            val errorMessage = "You must register or login first"
            return ResponseEntity.badRequest().body(errorMessage)
        }

            if (seat.isAvailable) {
                seat.isAvailable = false
                seatRepository.save(seat)
                val message = "You have booked seat ${seat.section}"
                return ResponseEntity.ok(message)
            } else {
                val errorMessage = "Seat ${seat.section} already booked"
                return ResponseEntity.badRequest().body(errorMessage)
            }
    } // returning booked seat with seat number and error message


    @PutMapping("/{id}")
    fun cancelSeat(@PathVariable id: Long, principal: Principal?): ResponseEntity<String> {

        val seat = seatRepository.findById(id).orElse(null) //move this underneath first if statement

        if (principal == null) {
            val errorMessage = "You must register or login first"
            return ResponseEntity.badRequest().body(errorMessage)
        } else {
            seat
        }

        if (seat != null) {
            seat.isAvailable = true
            seatRepository.save(seat)
            val message = "You have cancelled this booking, seat ${seat.section} is now available"
            return ResponseEntity.ok(message)
        } else {
            val errorMessage = "Seat not found"
            return ResponseEntity.badRequest().body(errorMessage)
        }
    } // returning cancelled seat with seat number and error message


    @DeleteMapping("/{id}")
    fun deleteSeat(@PathVariable id: Long) {
        seatRepository.deleteById(id)
    }

}

