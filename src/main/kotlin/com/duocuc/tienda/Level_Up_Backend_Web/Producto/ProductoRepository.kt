package com.duocuc.tienda.Level_Up_Backend_Web.Producto

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductoRepository : JpaRepository<Producto, Long> {
    // Como Producto está en el mismo paquete, no necesita import
}