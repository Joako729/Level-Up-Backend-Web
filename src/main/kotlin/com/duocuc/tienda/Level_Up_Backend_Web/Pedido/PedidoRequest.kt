package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

data class PedidoRequest(
    val usuarioId: Long,
    val total: Int,
    val productos: List<DetalleRequest>
)

data class DetalleRequest(
    val productoId: Long,
    val cantidad: Int,
    val precioUnitario: Int
)