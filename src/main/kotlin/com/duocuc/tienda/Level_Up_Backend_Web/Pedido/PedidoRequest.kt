package com.duocuc.tienda.Level_Up_Backend_Web.dto

// Esto es lo que React nos va a enviar
data class PedidoRequest(
    val usuarioId: Long,
    val productos: List<ProductoPedidoRequest>
)

data class ProductoPedidoRequest(
    val productoId: Long,
    val cantidad: Int
)