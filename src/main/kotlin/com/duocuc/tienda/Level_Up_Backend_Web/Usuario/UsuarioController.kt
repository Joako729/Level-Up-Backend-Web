package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = ["*"])
class UsuarioController(private val usuarioRepository: UsuarioRepository) {

    @GetMapping
    fun listarUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    // --- NUEVO: ENDPOINT PARA BORRAR USUARIO ---
    @DeleteMapping("/{id}")
    fun eliminarUsuario(@PathVariable id: Long) {
        usuarioRepository.deleteById(id)
    }
}