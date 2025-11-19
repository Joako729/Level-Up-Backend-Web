package com.duocuc.tienda.Level_Up_Backend_Web.controller

import com.duocuc.tienda.Level_Up_Backend_Web.model.Usuario
import com.duocuc.tienda.Level_Up_Backend_Web.repository.UsuarioRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/usuarios") // Esta será la URL
@CrossOrigin(origins = ["*"])    // Permite que React u otros se conecten
class UsuarioController(private val usuarioRepository: UsuarioRepository) {

    // Endpoint para ver todos los usuarios (GET)
    @GetMapping
    fun listarUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }
}