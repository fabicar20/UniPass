package edu.unicauca.unipass

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

/** Cuenta de usuario guardada en Room. La contraseña se guarda como hash, nunca en texto plano. */
@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["correo"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val claveHash: String
)