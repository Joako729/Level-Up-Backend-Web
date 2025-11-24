package com.duocuc.tienda.Level_Up_Backend_Web.Producto

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/productos")
class ProductoController(private val productoRepository: ProductoRepository) {

    @GetMapping
    fun listar(): List<Producto> = productoRepository.findAll()

    @PostMapping
    fun crear(@RequestBody producto: Producto): Producto = productoRepository.save(producto)

    // Necesario para que el panel pueda borrar
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        productoRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }
}