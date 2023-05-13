package com.example.cinema

import com.example.cinema.dtos.LoginDTO
import com.example.cinema.dtos.Message
import com.example.cinema.dtos.RegisterDTO
import com.example.cinema.models.Client
import com.example.cinema.services.ClientService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
//import org.springframework.boot.web.server.Cookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api")
class authController (private val clientService: ClientService){

    @PostMapping("register")
    fun Register(@RequestBody body: RegisterDTO): ResponseEntity<Client>{
       val client = Client()
        client.name = body.name
        client.email = body.email
        client.password = body.password

        return ResponseEntity.ok(this.clientService.save(client))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any>{
        val client = this.clientService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(Message("user not found"))
//        if (client == null)

        if(!client.comparePassword(body.password)){
            return ResponseEntity.badRequest().body(Message("invalid credencials"))
        }

        val issuer = client.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 )) // = 1 day
            .signWith(SignatureAlgorithm.HS256, "secret").compact() // .compact wraps everything and generate a string
        return ResponseEntity.ok(jwt) //returns JWT


    }

}

