package com.duocuc.tienda.Level_Up_Backend_Web.Usuario

import jakarta.persistence.*

@Entity
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var nombre: String = "",

    @Column(unique = true)
    var email: String = "",

    var password: String = "",

    // --- ESTO ES LO QUE FALTABA Y ROMPÍA TODO ---
    var rol: String = "CLIENTE"
)