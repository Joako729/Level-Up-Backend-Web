package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = ["*"])
class UsuarioController(private val usuarioRepository: UsuarioRepository) {

    // Endpoint para listar todos los usuarios (Solo ADMIN debería verlo, pero por ahora lo dejamos funcional)
    @GetMapping
    fun listarUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }
}