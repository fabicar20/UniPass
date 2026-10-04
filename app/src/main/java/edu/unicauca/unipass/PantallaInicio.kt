package edu.unicauca.unipass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon

@Composable
fun PantallaInicio(
    viewModel: OfertasViewModel,
    nombre: String = "",
    onOfertaClick: (Int) -> Unit = {}
) {

    val ofertas by viewModel.ofertas.collectAsState()

    var busqueda by remember {
        mutableStateOf(viewModel.consulta.value)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        // ---------- Encabezado ----------

        item {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "UniPass",
                fontSize = 28.sp
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = if (nombre.isBlank())
                    "Hola"
                else
                    "Hola, ${nombre.trim().substringBefore(" ")}",
                fontSize = 25.sp
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(
                text = "Encuentra oportunidades para tu carrera",
                fontSize = 18.sp
            )
        }

        // ---------- Barra de búsqueda ----------

        item {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = busqueda,
                onValueChange = {
                    busqueda = it
                    viewModel.buscar(it)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                placeholder = {
                    Text(
                        text = "¿Qué oportunidad estás buscando?",
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        // ---------- Filtros ----------

        item {

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Filtros rápidos",
                fontSize = 22.sp
            )
        }

        item {
            FiltrosOferta(viewModel)
        }

        // ---------- Limpiar filtros ----------

        item {

            Text(
                text = "Limpiar filtros",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    busqueda = ""
                    viewModel.buscar("")
                    viewModel.limpiarFiltros()
                }
            )
        }

        // ---------- Ofertas ----------

        item {

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Oportunidades para ti",
                fontSize = 24.sp
            )
        }

        // ---------- Sin resultados ----------

        if (ofertas.isEmpty()) {

            item {

                Text(
                    text = "No se encontraron ofertas.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // ---------- Lista de ofertas ----------

        items(
            ofertas,
            key = { it.id }
        ) { oferta ->

            OfertaCard(
                oferta = oferta,
                onClick = {
                    onOfertaClick(oferta.id)
                }
            ) {

                if (oferta.validaRequisitoGrado) {

                    Text(
                        text = "✓ Compatible con tu requisito de grado",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}