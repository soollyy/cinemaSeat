package com.example.cinema

import com.example.cinema.dtos.LoginDTO
import com.example.cinema.dtos.Message
import com.example.cinema.dtos.RegisterDTO
import com.example.cinema.models.Client
import com.example.cinema.services.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    fun login(@RequestBody body: LoginDTO): ResponseEntity<Any>{
        val client = this.clientService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(Message("user not found"))
//        if (client == null)

        if(!client.comparePassword(body.password)){
            return ResponseEntity.badRequest().body(Message("invalid credencials"))
        }


        return ResponseEntity.ok(client)
    }

}

