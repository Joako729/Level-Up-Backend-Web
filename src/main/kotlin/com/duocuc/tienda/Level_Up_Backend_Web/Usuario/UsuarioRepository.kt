package com.duocuc.tienda.Level_Up_Backend_Web.Usuario // <--- PAQUETE CORREGIDO

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByEmail(email: String): Usuario?
}