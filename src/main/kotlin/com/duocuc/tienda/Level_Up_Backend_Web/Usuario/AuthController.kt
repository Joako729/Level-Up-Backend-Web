package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

import com.duocuc.tienda.Level_Up_Backend_Web.Config.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService
) {

    @PostMapping("/register")
    fun registrar(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        if (usuarioRepository.findByEmail(usuario.email) != null) {
            return ResponseEntity.badRequest().body("El email ya está registrado")
        }
        if (usuario.rol.isEmpty()) usuario.rol = "CLIENTE"

        val nuevoUsuario = usuarioRepository.save(usuario)
        return ResponseEntity.ok(nuevoUsuario)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginData: Map<String, String>): ResponseEntity<Any> {
        val email = loginData["email"]
        val password = loginData["password"]

        val usuario = usuarioRepository.findByEmail(email ?: "")

        if (usuario != null && usuario.password == password) {
            val token = jwtService.generateToken(usuario.email, usuario.rol)

            // --- AQUÍ ESTÁ LA CLAVE: Enviamos todos los datos ---
            val response = mapOf(
                "token" to token,
                "email" to usuario.email, // <--- IMPORTANTE
                "rol" to usuario.rol,
                "nombre" to usuario.nombre,
                "id" to usuario.id
            )
            return ResponseEntity.ok(response)
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas")
    }
}