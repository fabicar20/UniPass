package edu.unicauca.unipass

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Filtros compartidos por Inicio y Buscar (mismo componente = mismos filtros).
 * Se combinan entre sí y con el texto de búsqueda. El chip activo se resalta
 * y muestra la opción elegida.
 */
@Composable
fun FiltrosOferta(viewModel: OfertasViewModel) {

    val ubicacion by viewModel.ubicacionSeleccionada.collectAsState()
    val modalidad by viewModel.modalidadSeleccionada.collectAsState()
    val tipo by viewModel.tipoSeleccionado.collectAsState()

    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FiltroDesplegable(
            titulo = "Ubicación",
            seleccion = ubicacion,
            opciones = listOf("Popayán, Cauca", "Remoto"),
            onElegir = viewModel::filtrarPorUbicacion
        )
        FiltroDesplegable(
            titulo = "Modalidad",
            seleccion = modalidad,
            opciones = listOf("Presencial", "Remoto", "Híbrido"),
            onElegir = viewModel::filtrarPorModalidad
        )
        FiltroDesplegable(
            titulo = "Tipo",
            seleccion = tipo,
            opciones = listOf("Pasantía", "Empleo"),
            onElegir = viewModel::filtrarPorTipo
        )
    }
}

@Composable
private fun FiltroDesplegable(
    titulo: String,
    seleccion: String?,
    opciones: List<String>,
    onElegir: (String?) -> Unit
) {
    var abierto by remember { mutableStateOf(false) }

    Box {
        FilterChip(
            selected = seleccion != null,
            onClick = { abierto = true },
            label = { Text(seleccion ?: titulo) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        )

        DropdownMenu(
            expanded = abierto,
            onDismissRequest = { abierto = false }
        ) {
            DropdownMenuItem(
                text = { Text("Cualquiera") },
                onClick = {
                    onElegir(null)
                    abierto = false
                }
            )
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onElegir(opcion)
                        abierto = false
                    }
                )
            }
        }
    }
}
