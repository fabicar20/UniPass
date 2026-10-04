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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPerfil(
    nombre: String = "",
    correo: String = "",
    onAcerca: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "Mi Perfil",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = nombre.ifBlank { "Estudiante" },
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (correo.isNotBlank()) {
                Text(
                    text = correo,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "Universidad del Cauca"
            )

            Text(
                text = "Tecnología en Telemática"
            )

            Text(
                text = "Estudiante universitario"
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
                        text = "Perfil completado",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "80% completado",
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Completa tu información para encontrar oportunidades más compatibles."
                    )
                }
            }
        }

        item {

            Text(
                text = "Habilidades",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Kotlin")
                    }
                )

                AssistChip(
                    onClick = {},
                    label = {
                        Text("html")
                    }
                )

                AssistChip(
                    onClick = {},
                    label = {
                        Text("Redes")
                    }
                )
            }
        }

        item {

            Text(
                text = "Idiomas",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Español — Nativo"
            )

            Text(
                text = "Portugués — Básico"
            )
        }

        item {

            Text(
                text = "Documentos",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Hoja de vida"
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Documento disponible",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onAcerca,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Acerca de UniPass y créditos")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
