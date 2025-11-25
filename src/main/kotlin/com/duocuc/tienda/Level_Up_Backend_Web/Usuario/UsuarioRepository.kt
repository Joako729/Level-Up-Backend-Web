package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    // Necesario para que el login y la búsqueda de pedidos funcionen
    fun findByEmail(email: String): Usuario?
}