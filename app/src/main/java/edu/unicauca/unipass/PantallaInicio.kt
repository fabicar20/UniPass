package edu.unicauca.unipass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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

@Composable
fun PantallaInicio(
    viewModel: OfertasViewModel,
    onOfertaClick: (Int) -> Unit = {}
) {

    val ofertas by viewModel.ofertas.collectAsState()
    var busqueda by remember { mutableStateOf(viewModel.consulta.value) }
    var menuUbicacionAbierto by remember { mutableStateOf(false) }
    var menuModalidadAbierto by remember { mutableStateOf(false) }
    var menuTipoAbierto by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "UniPass", fontSize = 28.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Hola, Fabiana", fontSize = 25.sp)

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Encuentra oportunidades para tu carrera",
                fontSize = 18.sp
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

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

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Filtros rápidos", fontSize = 22.sp)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text("Carrera") })
                Box {
                    AssistChip(
                        onClick = { menuUbicacionAbierto = true },
                        label = { Text("Ubicación") }
                    )

                    DropdownMenu(
                        expanded = menuUbicacionAbierto,
                        onDismissRequest = {
                            menuUbicacionAbierto = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Todas") },
                            onClick = {
                                viewModel.filtrarPorUbicacion(null)
                                menuUbicacionAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Popayán, Cauca") },
                            onClick = {
                                viewModel.filtrarPorUbicacion("Popayán, Cauca")
                                menuUbicacionAbierto = false
                            }
                        )
                    }
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {Box {
                AssistChip(
                    onClick = { menuModalidadAbierto = true },
                    label = { Text("Modalidad") }
                )

                DropdownMenu(
                    expanded = menuModalidadAbierto,
                    onDismissRequest = {
                        menuModalidadAbierto = false
                    }
                ) {
                    DropdownMenuItem(
                        text = { Text("Todas") },
                        onClick = {
                            viewModel.limpiarFiltros()
                            menuModalidadAbierto = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Presencial") },
                        onClick = {
                            viewModel.filtrarPorModalidad("Presencial")
                            menuModalidadAbierto = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Remoto") },
                        onClick = {
                            viewModel.filtrarPorModalidad("Remoto")
                            menuModalidadAbierto = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Híbrido") },
                        onClick = {
                            viewModel.filtrarPorModalidad("Híbrido")
                            menuModalidadAbierto = false
                        }
                    )
                }
            }
                Box {
                    AssistChip(
                        onClick = { menuTipoAbierto = true },
                        label = { Text("Tipo") }
                    )

                    DropdownMenu(
                        expanded = menuTipoAbierto,
                        onDismissRequest = {
                            menuTipoAbierto = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Todos") },
                            onClick = {
                                viewModel.limpiarFiltros()
                                menuTipoAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Pasantía") },
                            onClick = {
                                viewModel.filtrarPorTipo("Pasantía")
                                menuTipoAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Empleo") },
                            onClick = {
                                viewModel.filtrarPorTipo("Empleo")
                                menuTipoAbierto = false
                            }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Oportunidades para ti", fontSize = 24.sp)
        }

        if (ofertas.isEmpty()) {
            item {
                Text(
                    text = "No se encontraron ofertas.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(ofertas, key = { it.id }) { oferta ->
            OfertaCard(
                oferta = oferta,
                onClick = { onOfertaClick(oferta.id) }
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
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}