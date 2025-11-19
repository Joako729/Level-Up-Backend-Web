package com.duocuc.tienda.Level_Up_Backend_Web.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class DetallePedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val productoId: Long = 0,
    val nombreProducto: String = "",
    val precioUnitario: Int = 0,
    val cantidad: Int = 0,
    val subtotal: Int = 0,

    // Relación: Este detalle pertenece a un Pedido específico
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    val pedido: Pedido? = null
)