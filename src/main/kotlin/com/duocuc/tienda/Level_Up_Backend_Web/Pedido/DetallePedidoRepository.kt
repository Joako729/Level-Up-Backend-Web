package com.duocuc.tienda.Level_Up_Backend_Web.repository

import com.duocuc.tienda.Level_Up_Backend_Web.model.DetallePedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetallePedidoRepository : JpaRepository<DetallePedido, Long>