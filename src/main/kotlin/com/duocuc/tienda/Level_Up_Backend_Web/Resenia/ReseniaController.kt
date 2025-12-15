package com.duocuc.tienda.Level_Up_Backend_Web.Resenia

import com.duocuc.tienda.Level_Up_Backend_Web.Producto.ProductoRepository
import com.duocuc.tienda.Level_Up_Backend_Web.Usuario.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Date

@RestController
@RequestMapping("/api/resenias")
class ReseniaController(
    private val reseniaRepository: ReseniaRepository,
    private val productoRepository: ProductoRepository,
    private val usuarioRepository: UsuarioRepository
) {

    // DTO para recibir los datos desde el frontend
    data class ReseniaRequest(
        val productoId: Long,
        val emailUsuario: String, // Usaremos el email para identificar al usuario
        val comentario: String,
        val calificacion: Int
    )

    @GetMapping("/producto/{id}")
    fun listarPorProducto(@PathVariable id: Long): List<Resenia> {
        return reseniaRepository.findByProductoId(id)
    }

    @PostMapping
    fun crear(@RequestBody request: ReseniaRequest): ResponseEntity<Any> {
        val producto = productoRepository.findById(request.productoId)
        val usuario = usuarioRepository.findByEmail(request.emailUsuario)

        if (producto.isEmpty || usuario == null) {
            return ResponseEntity.badRequest().body("Producto o Usuario no encontrado")
        }

        val nuevaResenia = Resenia(
            comentario = request.comentario,
            calificacion = request.calificacion,
            fecha = Date(),
            usuario = usuario,
            producto = producto.get()
        )

        return ResponseEntity.ok(reseniaRepository.save(nuevaResenia))
    }
}