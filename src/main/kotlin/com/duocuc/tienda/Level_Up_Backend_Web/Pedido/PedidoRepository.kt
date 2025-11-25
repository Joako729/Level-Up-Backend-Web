package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository : JpaRepository<Pedido, Long> {

    // ESTA ES LA LÍNEA QUE TE FALTA Y CAUSA EL ERROR:
    fun findByUsuarioId(id: Long): List<Pedido>
}