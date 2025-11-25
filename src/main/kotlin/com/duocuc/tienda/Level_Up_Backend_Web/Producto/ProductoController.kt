package com.duocuc.tienda.Level_Up_Backend_Web.Producto

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/productos")
class ProductoController(private val productoRepository: ProductoRepository) {

    @GetMapping
    fun listar(): List<Producto> {
        return productoRepository.findAll()
    }

    @PostMapping
    fun crear(@RequestBody producto: Producto): Producto {
        return productoRepository.save(producto)
    }

    // --- NUEVO: EDITAR PRODUCTO ---
    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody producto: Producto): ResponseEntity<Producto> {
        return if (productoRepository.existsById(id)) {
            // Usamos copy para asegurar que el ID sea el correcto antes de guardar
            val productoActualizado = producto.copy(id = id)
            ResponseEntity.ok(productoRepository.save(productoActualizado))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }
}