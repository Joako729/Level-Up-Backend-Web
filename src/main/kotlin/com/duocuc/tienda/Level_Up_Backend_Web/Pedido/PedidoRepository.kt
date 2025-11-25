package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository : JpaRepository<Pedido, Long> {
    // Corregido: findByUsuario_Id
    fun findByUsuario_Id(id: Long): List<Pedido>
    fun findByUsuarioEmail(email: String): List<Pedido>
}