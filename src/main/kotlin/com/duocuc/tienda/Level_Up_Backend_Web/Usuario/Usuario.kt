package com.duocuc.tienda.Level_Up_Backend_Web.model

import jakarta.persistence.*

@Entity
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var nombre: String = "",

    @Column(unique = true) // El email no se puede repetir
    var email: String = "",

    var password: String = "" // Nota: En producción real esto se debe encriptar
)