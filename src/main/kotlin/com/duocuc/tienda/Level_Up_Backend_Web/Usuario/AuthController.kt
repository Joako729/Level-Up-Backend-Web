package com.duocuc.tienda.Level_Up_Backend_Web.controller

import com.duocuc.tienda.Level_Up_Backend_Web.model.Usuario
import com.duocuc.tienda.Level_Up_Backend_Web.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"]) // IMPORTANTE: Permite que React se conecte
class AuthController(private val usuarioRepository: UsuarioRepository) {

    // Endpoint para REGISTRARSE
    @PostMapping("/register")
    fun registrar(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(usuario.email) != null) {
            return ResponseEntity.badRequest().body("El email ya está registrado")
        }
        val nuevoUsuario = usuarioRepository.save(usuario)
        return ResponseEntity.ok(nuevoUsuario)
    }

    // Endpoint para LOGIN
    @PostMapping("/login")
    fun login(@RequestBody loginData: Map<String, String>): ResponseEntity<Any> {
        val email = loginData["email"]
        val password = loginData["password"]

        val usuario = usuarioRepository.findByEmail(email ?: "")

        if (usuario != null && usuario.password == password) {
            return ResponseEntity.ok(usuario) // Login exitoso
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas")
    }
}