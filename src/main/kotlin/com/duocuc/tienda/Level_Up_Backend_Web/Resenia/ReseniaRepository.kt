package com.duocuc.tienda.Level_Up_Backend_Web.Resenia

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReseniaRepository : JpaRepository<Resenia, Long> {
    // Buscar todas las reseñas de un producto específico
    fun findByProductoId(productoId: Long): List<Resenia>
}