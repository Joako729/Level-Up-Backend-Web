package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

import jakarta.persistence.*

@Entity
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var nombre: String = "",

    // --- AGREGAR ESTOS CAMPOS NUEVOS ---
    var username: String = "",

    var edad: Int = 0,

    var region: String = "",

    var comuna: String = "",

    @Column(unique = true)
    var email: String = "",

    var password: String = "",

    var rol: String = "CLIENTE"
)