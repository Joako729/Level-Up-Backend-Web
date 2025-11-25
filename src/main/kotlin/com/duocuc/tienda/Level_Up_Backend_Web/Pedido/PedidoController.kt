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
    private val usuarioRepository: UsuarioRepository // Inyectamos esto para buscar el ID
) {

    // ADMIN: Ve TODO
    @GetMapping
    fun listar(): List<Pedido> {
        return pedidoService.listarPedidos()
    }

    // CLIENTE: Ve SOLO SUYOS (Buscando por ID)
    @GetMapping("/mis-pedidos")
    fun misPedidos(): List<Pedido> {
        // 1. Sacamos el email del token
        val email = SecurityContextHolder.getContext().authentication.name

        // 2. Buscamos al usuario real en la BD para obtener su ID
        val usuario = usuarioRepository.findByEmail(email)

        // 3. Si existe, buscamos sus pedidos por ID
        return if (usuario != null) {
            pedidoRepository.findByUsuarioId(usuario.id)
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