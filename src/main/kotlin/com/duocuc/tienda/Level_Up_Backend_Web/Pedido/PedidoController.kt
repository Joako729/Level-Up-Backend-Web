package com.duocuc.tienda.Level_Up_Backend_Web.controller

import com.duocuc.tienda.Level_Up_Backend_Web.dto.PedidoRequest
import com.duocuc.tienda.Level_Up_Backend_Web.model.Pedido
import com.duocuc.tienda.Level_Up_Backend_Web.service.PedidoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = ["*"])
class PedidoController(private val pedidoService: PedidoService) {

    // Para ver la lista en el navegador (GET)
    @GetMapping
    fun listar(): List<Pedido> {
        return pedidoService.listarPedidos()
    }

    // Para crear un pedido nuevo (POST)
    @PostMapping
    fun crearPedido(@RequestBody request: PedidoRequest): ResponseEntity<Any> {
        return try {
            val pedido = pedidoService.generarPedido(request)
            ResponseEntity.ok(pedido)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error al procesar pedido: ${e.message}")
        }
    }
}