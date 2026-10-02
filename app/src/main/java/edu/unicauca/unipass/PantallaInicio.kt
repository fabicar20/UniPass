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
fun PantallaInicio(
    modifier: Modifier = Modifier,
    onOfertaClick: () -> Unit = {}

) {

    var busqueda by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "UniPass",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Hola, Fabiana ",
                fontSize = 25.sp
            )

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

            Text(
                text = "Filtros rápidos",
                fontSize = 22.sp
            )
        }

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Carrera")
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Modalidad")
                    }
                )

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Tipo")
                    }
                )
            }
        }

        item {

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Oportunidades para ti",
                fontSize = 24.sp
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
                        fontSize = 19.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Empresa Tech",
                        fontSize = 15.sp
                    )

                    Text(
                        text = " Popayán, Cauca",
                        fontSize = 15.sp
                    )

                    Text(
                        text = " Pasantía",
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Compatible con tu requisito de grado",
                        fontSize = 14.sp,
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
                        text = "Practicante de Redes y Telecomunicaciones",
                        fontSize = 19.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Empresa de Telecomunicaciones",
                        fontSize = 15.sp
                    )

                    Text(
                        text = " Popayán, Cauca",
                        fontSize = 15.sp
                    )

                    Text(
                        text = " Pasantía",
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

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