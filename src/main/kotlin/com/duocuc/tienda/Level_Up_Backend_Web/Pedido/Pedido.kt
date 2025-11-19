package com.duocuc.tienda.Level_Up_Backend_Web.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val fecha: LocalDateTime = LocalDateTime.now(),

    var total: Int = 0,

    var estado: String = "CONFIRMADO",

    // Relación: Un pedido pertenece a un Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario? = null
)