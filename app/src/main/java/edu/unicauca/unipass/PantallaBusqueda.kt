package edu.unicauca.unipass

import androidx.compose.foundation.clickable
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
fun PantallaBusqueda(
    viewModel: OfertasViewModel,
    onOfertaClick: (Int) -> Unit = {}
) {

    val ofertas by viewModel.ofertas.collectAsState()

    var busqueda by remember {
        mutableStateOf(viewModel.consulta.value)
    }

    var menuUbicacionAbierto by remember {
        mutableStateOf(false)
    }

    var menuModalidadAbierto by remember {
        mutableStateOf(false)
    }

    var menuTipoAbierto by remember {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ---------- TÍTULO Y BÚSQUEDA ----------

        item {

            Text(
                text = "Buscar oportunidades",
                fontSize = 26.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
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
                    Text("Buscar una oportunidad")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        // ---------- TÍTULO FILTROS ----------

        item {
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // ---------- UBICACIÓN Y MODALIDAD ----------

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // UBICACIÓN

                Box {

                    AssistChip(
                        onClick = {
                            menuUbicacionAbierto = true
                        },
                        label = {
                            Text("Ubicación")
                        }
                    )

                    DropdownMenu(
                        expanded = menuUbicacionAbierto,
                        onDismissRequest = {
                            menuUbicacionAbierto = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text("Todas")
                            },
                            onClick = {
                                viewModel.filtrarPorUbicacion(null)
                                menuUbicacionAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Popayán, Cauca")
                            },
                            onClick = {
                                viewModel.filtrarPorUbicacion(
                                    "Popayán, Cauca"
                                )
                                menuUbicacionAbierto = false
                            }
                        )
                    }
                }

                // MODALIDAD

                Box {

                    AssistChip(
                        onClick = {
                            menuModalidadAbierto = true
                        },
                        label = {
                            Text("Modalidad")
                        }
                    )

                    DropdownMenu(
                        expanded = menuModalidadAbierto,
                        onDismissRequest = {
                            menuModalidadAbierto = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text("Todas")
                            },
                            onClick = {
                                viewModel.limpiarFiltros()
                                menuModalidadAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Presencial")
                            },
                            onClick = {
                                viewModel.filtrarPorModalidad(
                                    "Presencial"
                                )
                                menuModalidadAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Remoto")
                            },
                            onClick = {
                                viewModel.filtrarPorModalidad(
                                    "Remoto"
                                )
                                menuModalidadAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Híbrido")
                            },
                            onClick = {
                                viewModel.filtrarPorModalidad(
                                    "Híbrido"
                                )
                                menuModalidadAbierto = false
                            }
                        )
                    }
                }
            }
        }

        // ---------- TIPO ----------

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Box {

                    AssistChip(
                        onClick = {
                            menuTipoAbierto = true
                        },
                        label = {
                            Text("Tipo")
                        }
                    )

                    DropdownMenu(
                        expanded = menuTipoAbierto,
                        onDismissRequest = {
                            menuTipoAbierto = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text("Todos")
                            },
                            onClick = {
                                viewModel.limpiarFiltros()
                                menuTipoAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Pasantía")
                            },
                            onClick = {
                                viewModel.filtrarPorTipo(
                                    "Pasantía"
                                )
                                menuTipoAbierto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Empleo")
                            },
                            onClick = {
                                viewModel.filtrarPorTipo(
                                    "Empleo"
                                )
                                menuTipoAbierto = false
                            }
                        )
                    }
                }
            }
        }

        // ---------- RESULTADOS ----------

        item {

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "${ofertas.size} oportunidades encontradas",
                fontSize = 18.sp
            )

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

        // ---------- LISTA DE OFERTAS ----------

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
                        text = "✓ Requisito de grado",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}