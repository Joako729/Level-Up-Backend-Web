package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

import com.duocuc.tienda.Level_Up_Backend_Web.Usuario.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pedidos")
class PedidoController(
    private val pedidoService: PedidoService,
    private val pedidoRepository: PedidoRepository,
    private val usuarioRepository: UsuarioRepository
) {

    @GetMapping
    fun listar(): List<Pedido> {
        return pedidoService.listarPedidos()
    }

    @GetMapping("/mis-pedidos")
    fun misPedidos(): List<Pedido> {
        val email = SecurityContextHolder.getContext().authentication.name
        val usuario = usuarioRepository.findByEmail(email)

        return if (usuario != null) {
            // Usamos la función corregida
            pedidoRepository.findByUsuario_Id(usuario.id)
        } else {
            emptyList()
        }
    }

    @PostMapping
    fun crearPedido(@RequestBody request: PedidoRequest): ResponseEntity<Any> {
        return try {
            val pedido = pedidoService.generarPedido(request)
            ResponseEntity.ok(pedido)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }
}