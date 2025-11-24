package com.duocuc.tienda.Level_Up_Backend_Web.Producto // <--- AHORA COINCIDE CON LA CARPETA

import jakarta.persistence.*

@Entity
data class Producto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var nombre: String = "",
    var precio: Int = 0,
    var stock: Int = 0,
    var categoria: String = "",
    var descripcion: String = "",
    var imagen: String = ""
)