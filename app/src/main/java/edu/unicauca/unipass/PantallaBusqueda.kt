package edu.unicauca.unipass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaBusqueda(
    onOfertaClick: () -> Unit = {}
) {

    var busqueda by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "Buscar oportunidades",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = busqueda,
                onValueChange = {
                    busqueda = it
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

        item {

            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Universidad")
                    }
                )

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Carrera")
                    }
                )
            }
        }

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Semestre")
                    }
                )

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Ubicación")
                    }
                )
            }
        }

        item {

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "12 oportunidades encontradas",
                fontSize = 18.sp
            )

            Text(
                text = "Limpiar filtros",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            )
        }

        item {

            Card(
                onClick = {
                    onOfertaClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    Text(
                        text = "Practicante de Desarrollo de Software",
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Empresa Tech")

                    Text(" Popayán, Cauca")

                    Text(" Pasantía")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Requisito de grado",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {

            Card(
                onClick = {
                    onOfertaClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    Text(
                        text = "Analista de Datos Junior",
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Data Solutions")

                    Text(" Remoto")

                    Text(" Pasantía")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Requisito de grado",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {

            Card(
                onClick = {
                    onOfertaClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    Text(
                        text = "Practicante de Redes",
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Telecomunicaciones del Cauca")

                    Text(" Popayán, Cauca")

                    Text(" Pasantía")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Requisito de grado",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}