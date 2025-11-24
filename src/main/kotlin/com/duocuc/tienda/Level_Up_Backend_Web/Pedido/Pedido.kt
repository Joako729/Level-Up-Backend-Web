package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

// Importamos Usuario porque está en OTRA carpeta
import com.duocuc.tienda.Level_Up_Backend_Web.Usuario.Usuario
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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario? = null
)