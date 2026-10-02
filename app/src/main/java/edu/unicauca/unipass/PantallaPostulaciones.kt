package edu.unicauca.unipass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPostulaciones() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "Mis postulaciones",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Aquí puedes consultar el estado de tus postulaciones.",
                fontSize = 16.sp
            )
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Analista de Datos Junior",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Data Solutions"
                    )

                    Text(
                        text = " Remoto"
                    )

                    Text(
                        text = " Pasantía"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Estado: En revisión",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Practicante de Redes",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Telecomunicaciones del Cauca"
                    )

                    Text(
                        text = " Popayán, Cauca"
                    )

                    Text(
                        text = " Pasantía"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Estado: Postulación enviada",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Practicante de Desarrollo de Software",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Empresa Tech"
                    )

                    Text(
                        text = " Popayán, Cauca"
                    )

                    Text(
                        text = "Pasantía"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Estado: Entrevista",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}