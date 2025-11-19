package com.duocuc.tienda.Level_Up_Backend_Web.repository

import com.duocuc.tienda.Level_Up_Backend_Web.model.Pedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository : JpaRepository<Pedido, Long> {
    // Para ver el historial de compras de un usuario
    fun findByUsuarioId(usuarioId: Long): List<Pedido>
}