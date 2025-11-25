package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

import com.duocuc.tienda.Level_Up_Backend_Web.Usuario.UsuarioRepository
import com.duocuc.tienda.Level_Up_Backend_Web.Producto.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val productoRepository: ProductoRepository
) {

    fun listarPedidos(): List<Pedido> {
        return pedidoRepository.findAll()
    }

    @Transactional
    fun generarPedido(request: PedidoRequest): Pedido {
        // 1. Buscar usuario
        val usuario = usuarioRepository.findById(request.usuarioId)
            .orElseThrow { RuntimeException("Usuario no encontrado") }

        // 2. Crear la estructura del Pedido (Aún sin guardar)
        val nuevoPedido = Pedido(
            usuario = usuario,
            fecha = LocalDateTime.now(),
            estado = "CONFIRMADO",
            total = 0 // Lo calculamos abajo
        )

        var totalCalculado = 0
        val listaDetalles = mutableListOf<DetallePedido>()

        // 3. Preparar los detalles
        request.productos.forEach { item ->
            val productoReal = productoRepository.findById(item.productoId)
                .orElseThrow { RuntimeException("Producto ID ${item.productoId} no existe") }

            val subtotal = productoReal.precio * item.cantidad
            totalCalculado += subtotal

            // Creamos el detalle vinculado al pedido padre
            val detalle = DetallePedido(
                pedido = nuevoPedido,
                productoId = productoReal.id,
                nombreProducto = productoReal.nombre,
                precioUnitario = productoReal.precio,
                cantidad = item.cantidad,
                subtotal = subtotal
            )
            listaDetalles.add(detalle)
        }

        // 4. Asignamos datos finales
        nuevoPedido.total = totalCalculado
        nuevoPedido.detalles = listaDetalles // ¡Esto es lo que faltaba!

        // 5. Guardamos TODO de una vez (Cascade Type ALL se encarga de los detalles)
        return pedidoRepository.save(nuevoPedido)
    }
}