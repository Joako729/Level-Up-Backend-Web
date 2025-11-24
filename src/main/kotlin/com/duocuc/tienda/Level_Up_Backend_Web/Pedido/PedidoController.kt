package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = ["*"])
class PedidoController(private val pedidoService: PedidoService) {

    @GetMapping
    fun listar(): List<Pedido> {
        return pedidoService.listarPedidos()
    }

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