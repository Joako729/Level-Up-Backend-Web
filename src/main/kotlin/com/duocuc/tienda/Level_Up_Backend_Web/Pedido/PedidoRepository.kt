package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository : JpaRepository<Pedido, Long> {
    // Búsqueda exacta por ID de usuario (Más seguro que el email)
    fun findByUsuarioId(id: Long): List<Pedido>

    // Mantenemos esta por si acaso, pero usaremos la de arriba
    fun findByUsuarioEmail(email: String): List<Pedido>
}