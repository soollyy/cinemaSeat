package com.example.cinema

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class CinemaApplication

fun main(args: Array<String>) {
	runApplication<CinemaApplication>(*args)
}