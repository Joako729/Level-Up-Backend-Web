package com.duocuc.tienda.Level_Up_Backend_Web.Resenia

import com.duocuc.tienda.Level_Up_Backend_Web.Producto.Producto
import com.duocuc.tienda.Level_Up_Backend_Web.Usuario.Usuario
import jakarta.persistence.*
import java.util.Date

@Entity
data class Resenia(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(length = 1000)
    var comentario: String = "",

    var calificacion: Int = 1, // Del 1 al 5

    var fecha: Date = Date(),

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    var usuario: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "producto_id")
    var producto: Producto? = null
)