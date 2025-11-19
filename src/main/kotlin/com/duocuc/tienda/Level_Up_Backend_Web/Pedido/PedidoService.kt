package com.duocuc.tienda.Level_Up_Backend_Web.service

import com.duocuc.tienda.Level_Up_Backend_Web.dto.PedidoRequest
import com.duocuc.tienda.Level_Up_Backend_Web.model.DetallePedido
import com.duocuc.tienda.Level_Up_Backend_Web.model.Pedido
import com.duocuc.tienda.Level_Up_Backend_Web.repository.DetallePedidoRepository
import com.duocuc.tienda.Level_Up_Backend_Web.repository.PedidoRepository
import com.duocuc.tienda.Level_Up_Backend_Web.repository.ProductoRepository
import com.duocuc.tienda.Level_Up_Backend_Web.repository.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val detallePedidoRepository: DetallePedidoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val productoRepository: ProductoRepository
) {

    @Transactional
    fun generarPedido(request: PedidoRequest): Pedido {
        // 1. Buscar al usuario
        val usuario = usuarioRepository.findById(request.usuarioId)
            .orElseThrow { RuntimeException("Usuario no encontrado") }

        // 2. Crear el Pedido (Cabecera) inicialmente con total 0
        val nuevoPedido = Pedido(usuario = usuario, total = 0)
        val pedidoGuardado = pedidoRepository.save(nuevoPedido)

        var totalCalculado = 0

        // 3. Recorrer productos
        request.productos.forEach { item ->
            val productoReal = productoRepository.findById(item.productoId)
                .orElseThrow { RuntimeException("Producto ID ${item.productoId} no existe") }

            val subtotal = productoReal.precio * item.cantidad
            totalCalculado += subtotal

            val detalle = DetallePedido(
                pedido = pedidoGuardado,
                productoId = productoReal.id,
                nombreProducto = productoReal.nombre,
                precioUnitario = productoReal.precio,
                cantidad = item.cantidad,
                subtotal = subtotal
            )
            detallePedidoRepository.save(detalle)
        }

        // 4. Actualizar el total y guardar
        pedidoGuardado.total = totalCalculado
        return pedidoRepository.save(pedidoGuardado)
    }

    // 👇👇👇 AGREGAMOS ESTA FUNCIÓN NUEVA 👇👇👇
    fun listarPedidos(): List<Pedido> {
        return pedidoRepository.findAll()
    }
}