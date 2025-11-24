package com.duocuc.tienda.Level_Up_Backend_Web.Pedido // <--- PAQUETE PEDIDO

import jakarta.persistence.*

@Entity
data class DetallePedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    val pedido: Pedido? = null,

    val productoId: Long = 0,
    val nombreProducto: String = "",
    val precioUnitario: Int = 0,
    val cantidad: Int = 0,
    val subtotal: Int = 0
)