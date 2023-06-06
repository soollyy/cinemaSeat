package com.example.cinema


import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.mockito.Mockito.`when`
import java.util.Optional
import org.mockito.InjectMocks
import org.mockito.Mock

@SpringBootTest
@AutoConfigureMockMvc
class SeatTests {

	@InjectMocks
	lateinit var seatController: SeatController

	@Mock
	lateinit var seatRepository: SeatRepository

	@Autowired
	private lateinit var mockMvc: MockMvc


	@Test //testing available seats
	fun testGetAvailableSeats() {
		mockMvc.perform(MockMvcRequestBuilders.get("/seats"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andReturn()
	}

	@Test // test for data
	fun testSeatDataClass() {
		val seat = Seat(1, "A", true)
		assertEquals(1, seat.id)
		assertEquals("A", seat.section)
		assertEquals(true, seat.isAvailable)
	}

	@Test //test for seats list
	fun testSeatsList() {
		assertEquals(6, seats.size)
		assertEquals(1, seats.count { it.section == "A" && !it.isAvailable })
		assertEquals(1, seats.count { it.section == "B" && !it.isAvailable })
		assertEquals(0, seats.count { it.section == "C" && !it.isAvailable })
	}


//	@Test //testing for booking a seat, step by step
//	fun bookSeatTest() {
//		val seat = Seat(1, "A", true)
//		`when`(seatRepository.findById(1)).thenReturn(Optional.of(seat)) //given
//		val result = seatController.bookSeat(1) //when
//		assertEquals(false, seat.isAvailable)//then
//	}


	//tests for cancelled seats


	@Test //tests for deleted seats
	fun testDeleteSeat() {
		mockMvc.perform(MockMvcRequestBuilders.delete("/seats/1"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.content().string(""))
	}
}