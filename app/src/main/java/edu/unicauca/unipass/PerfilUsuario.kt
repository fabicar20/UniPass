package edu.unicauca.unipass

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Datos del perfil que cada usuario llena. Una fila por usuario. */
@Entity(tableName = "perfiles")
data class PerfilUsuario(
    @PrimaryKey
    val usuarioId: Int,
    val universidad: String = "",
    val carrera: String = "",
    val semestre: String = "",
    val habilidades: String = "",   // separadas por comas
    val idiomas: String = "",       // uno por línea
    val sobreMi: String = ""
)