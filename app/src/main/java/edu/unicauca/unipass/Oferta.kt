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
    val modalidad: String,          // Presencial, Remoto, Híbrido
    val tipo: String,               // Pasantía, Empleo, etc.
    val remuneracion: String,

    // Datos para la pantalla de detalle
    val duracion: String = "",
    val horario: String = "",
    val responsabilidades: String = "",   // una por línea (separadas por \n)
    val requisitos: String = "",          // uno por línea (separados por \n)
    val validaRequisitoGrado: Boolean = true,

    // Estado del usuario sobre la oferta
    val guardada: Boolean = false,
    val estadoPostulacion: String? = null // null = no postulado
) {
    val listaResponsabilidades: List<String>
        get() = responsabilidades.lines().filter { it.isNotBlank() }

    val listaRequisitos: List<String>
        get() = requisitos.lines().filter { it.isNotBlank() }
}
