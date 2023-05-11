package com.example.cinema

import jakarta.persistence.Table
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

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
)


@RestController
@RequestMapping("/seats")
class SeatController (private val seatRepository: SeatRepository) {

    @GetMapping
    fun getAvailableSeats(): List<Seat> {
        return seatRepository.findAll().filter { it.isAvailable }
    } // returning all the available seats


    @PostMapping("/{id}")
    fun bookSeat(@PathVariable id: Long): Seat {
        val seat = seatRepository.findById(id).get()
        seat.isAvailable = false
        seatRepository.save(seat)
        return seat
    } //returning booked seat


    @PutMapping("/{id}")
    fun cancelSeat(@PathVariable id: Long): Seat {
        val seat = seatRepository.findById(id).get()
        seat.isAvailable = true
        seatRepository.save(seat)
        return seat
    } // returning cancelled seat


    @DeleteMapping("/{id}")
    fun deleteSeat(@PathVariable id: Long) {
        seatRepository.deleteById(id)
    }
    // added "/seats/{id}" to the endpoint to make it look sense instead of "/{id}"

}

