package com.duocuc.tienda.Level_Up_Backend_Web.Pedido

// Importamos lo que está en carpetas externas
import com.duocuc.tienda.Level_Up_Backend_Web.Usuario.UsuarioRepository
import com.duocuc.tienda.Level_Up_Backend_Web.Producto.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PedidoService(
    // Todos estos repositorios ahora se encuentran porque corregimos sus paquetes
    private val pedidoRepository: PedidoRepository,
    private val detallePedidoRepository: DetallePedidoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val productoRepository: ProductoRepository
) {

    fun listarPedidos(): List<Pedido> {
        return pedidoRepository.findAll()
    }

    @Transactional
    fun generarPedido(request: PedidoRequest): Pedido {
        // 1. Buscar usuario (Usando el repo importado de Usuario)
        val usuario = usuarioRepository.findById(request.usuarioId)
            .orElseThrow { RuntimeException("Usuario no encontrado") }

        // 2. Crear cabecera
        val nuevoPedido = Pedido(usuario = usuario, total = 0)
        val pedidoGuardado = pedidoRepository.save(nuevoPedido)

        var totalCalculado = 0

        // 3. Procesar productos
        request.productos.forEach { item ->
            // Usando el repo importado de Producto
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

        // 4. Finalizar
        pedidoGuardado.total = totalCalculado
        return pedidoRepository.save(pedidoGuardado)
    }
}