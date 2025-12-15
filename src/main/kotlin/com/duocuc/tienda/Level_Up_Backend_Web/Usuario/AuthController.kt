package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

import com.duocuc.tienda.Level_Up_Backend_Web.Config.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder // Asegúrate de tener este import si usas encoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService
) {

    // Clase interna para recibir los datos del login
    data class LoginRequest(val email: String, val password: String)
    data class RegisterRequest(val nombre: String, val email: String, val password: String)

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        // 1. Buscamos al usuario por email
        val usuario = usuarioRepository.findByEmail(request.email)

        // 2. Validamos si existe y si la contraseña coincide
        // NOTA: Si usas encriptación en registro, usa: passwordEncoder.matches(request.password, usuario.password)
        if (usuario != null && usuario.password == request.password) {

            // 3. Generamos el token incluyendo el ROL
            val token = jwtService.generateToken(usuario.email, usuario.rol)

            // 4. RESPUESTA COMPLETA (Esto es lo que faltaba)
            // Enviamos el rol, nombre, id y email para que el Frontend los guarde
            val response = mapOf(
                "token" to token,
                "rol" to usuario.rol,     // <--- IMPORTANTE: Enviar el rol
                "nombre" to usuario.nombre,
                "email" to usuario.email,
                "id" to usuario.id
            )
            return ResponseEntity.ok(response)
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas")
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Any> {
        if (usuarioRepository.findByEmail(request.email) != null) {
            return ResponseEntity.badRequest().body("El email ya está registrado")
        }

        val nuevoUsuario = Usuario(
            nombre = request.nombre,
            email = request.email,
            password = request.password, // Idealmente encriptar aquí
            rol = "CLIENTE", // Por defecto todos son clientes
            username = request.email // Opcional si usas username
        )

        usuarioRepository.save(nuevoUsuario)
        return ResponseEntity.ok(mapOf("message" to "Usuario registrado con éxito"))
    }
}