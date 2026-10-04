package edu.unicauca.unipass

import androidx.room.Entity

/**
 * Lo que cada usuario hizo con cada oferta: si la guardó y/o se postuló.
 * Una fila por pareja (usuario, oferta).
 */
@Entity(
    tableName = "ofertas_usuario",
    primaryKeys = ["usuarioId", "ofertaId"]
)
data class OfertaUsuario(
    val usuarioId: Int,
    val ofertaId: Int,
    val guardada: Boolean = false,
    val estadoPostulacion: String? = null   // null = no se ha postulado
)