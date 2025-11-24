package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

// Importamos el servicio de seguridad que creamos en la carpeta Config
import com.duocuc.tienda.Level_Up_Backend_Web.Config.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
// Nota: Hemos quitado @CrossOrigin de aquí porque ya lo configuraste globalmente en SecurityConfig.
class AuthController(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService
) {

    // --- ENDPOINT DE REGISTRO ---
    @PostMapping("/register")
    fun registrar(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        // 1. Verificamos si el email ya existe
        if (usuarioRepository.findByEmail(usuario.email) != null) {
            return ResponseEntity.badRequest().body("El email ya está registrado")
        }

        // 2. Asignamos el rol por defecto si viene vacío
        if (usuario.rol.isEmpty()) {
            usuario.rol = "CLIENTE"
        }

        // 3. Guardamos en la base de datos
        val nuevoUsuario = usuarioRepository.save(usuario)
        return ResponseEntity.ok(nuevoUsuario)
    }

    // --- ENDPOINT DE LOGIN ---
    @PostMapping("/login")
    fun login(@RequestBody loginData: Map<String, String>): ResponseEntity<Any> {
        val email = loginData["email"]
        val password = loginData["password"]

        // 1. Buscamos al usuario por email
        val usuario = usuarioRepository.findByEmail(email ?: "")

        // 2. Verificamos si existe y si la contraseña coincide
        if (usuario != null && usuario.password == password) {

            // 3. Generamos el Token JWT (La llave de acceso)
            val token = jwtService.generateToken(usuario.email, usuario.rol)

            // 4. Preparamos la respuesta para el Frontend (React)
            // Es vital enviar el ROL y el NOMBRE para que la web sepa qué mostrar
            val response = mapOf(
                "token" to token,
                "email" to usuario.email,
                "rol" to usuario.rol,
                "nombre" to usuario.nombre
            )
            return ResponseEntity.ok(response)
        }

        // Si falla, devolvemos error 401 (No autorizado)
        return ResponseEntity.status(401).body("Credenciales incorrectas")
    }
}