package edu.unicauca.unipass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ofertas")
data class Oferta(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val titulo: String,
    val empresa: String,
    val ubicacion: String,
    val modalidad: String,
    val tipo: String,
    val remuneracion: String
)