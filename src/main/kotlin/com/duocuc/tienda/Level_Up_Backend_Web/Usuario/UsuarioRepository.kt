package com.duocuc.tienda.Level_Up_Backend_Web.repository

import com.duocuc.tienda.Level_Up_Backend_Web.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    // Spring crea esta consulta automáticamente solo por el nombre de la función
    fun findByEmail(email: String): Usuario?
}