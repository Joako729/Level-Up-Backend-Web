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

    // ADMIN: Ve TODO
    @GetMapping
    fun listar(): List<Pedido> {
        return pedidoService.listarPedidos()
    }

    // CLIENTE: Ve SOLO SUYOS
    @GetMapping("/mis-pedidos")
    fun misPedidos(): List<Pedido> {
        val email = SecurityContextHolder.getContext().authentication.name
        val usuario = usuarioRepository.findByEmail(email)

        // Aquí ya no dará error porque actualizaste PedidoRepository
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

    // BORRAR PEDIDO (Solo Admin)
    @DeleteMapping("/{id}")
    fun eliminarPedido(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            if (pedidoRepository.existsById(id)) {
                pedidoRepository.deleteById(id)
                ResponseEntity.ok().body(mapOf("message" to "Pedido eliminado"))
            } else {
                ResponseEntity.status(404).body("Pedido no encontrado")
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Error al eliminar: ${e.message}")
        }
    }
}