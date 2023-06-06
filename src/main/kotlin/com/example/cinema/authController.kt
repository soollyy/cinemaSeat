package com.example.cinema

import com.example.cinema.dtos.LoginDTO
import com.example.cinema.dtos.AuthorisationDTO
import com.example.cinema.dtos.RegisterDTO
import com.example.cinema.models.Client
import com.example.cinema.repositories.ClientRepository
import com.example.cinema.services.ClientService
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api")
class authController(
    private val clientService: ClientService,
    private val client: ClientRepository,
){

    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<Client>{
       val client = Client()
        client.name = body.name
        client.email = body.email
        client.password = body.password

        return ResponseEntity.ok(this.clientService.save(client))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {

        val client = this.clientService.findByEmail(body.email)
        if (client == null) {
            val errorMessage = "user not found"
            return ResponseEntity.badRequest().body(errorMessage)
        }

        if (!client.comparePassword(body.password)) {
            val errorMessage = "invalid credentials"
            return ResponseEntity.badRequest().body(errorMessage)
        }

        val issuer = client.id.toString() //assigning value issuer to client.id.toString():
        //client is the user
        //id returns an identifier
        //toString() returns a string representation of the object's value


        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // = 1 day
            .signWith(SignatureAlgorithm.HS256, "secret").compact() // .compact wraps everything and generate a string
        return ResponseEntity.ok(jwt) //returns JWT
    }

    @PostMapping("authorise")
    fun authorise(@RequestBody body: AuthorisationDTO): ResponseEntity<Any> {
        try {
            val jwtToken = body.token

            val claims = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(jwtToken)
                .body // parses and validate the jwt token

            val issuer = claims.issuer

            val expectedIssuer = "1"
            if (issuer != expectedIssuer) {
                val errorMessage = "Invalid issuer"
                return ResponseEntity.badRequest().body(errorMessage)
            } // authorisation logic

            return ResponseEntity.ok("User authorised successfully") // Return a success response if authorised
        } catch (e: JwtException) {
            val errorMessage = "Invalid or expired token"
            return ResponseEntity.badRequest().body(errorMessage)  // Return an error response if the token is invalid or expired
        }
    }
}

