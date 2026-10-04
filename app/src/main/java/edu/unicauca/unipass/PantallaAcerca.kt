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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val integrantes = listOf(
    "Fabiana Zuleima Cárdenas",
    "Karol Dayana Ocoro Natez",
    "Leiner Santiago Claros"
)

@Composable
fun PantallaAcerca(onVolver: () -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onVolver) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
                Text(
                    text = "Acerca de UniPass",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "¿Qué es UniPass?",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "UniPass es una aplicación para estudiantes universitarios " +
                                "que necesitan encontrar una pasantía o práctica profesional " +
                                "(requisito de grado en muchas universidades) y todavía no " +
                                "tienen experiencia laboral. Reúne las ofertas en un solo " +
                                "lugar, indica cuáles sirven como requisito de grado y permite " +
                                "buscar, guardar y postularse en pocos pasos."
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Qué puedes hacer",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Crear una cuenta e iniciar sesión")
                    Text("• Buscar ofertas y filtrarlas por ubicación, modalidad y tipo")
                    Text("• Ver el detalle de cada oferta y sus requisitos")
                    Text("• Guardar ofertas para verlas después")
                    Text("• Postularte y seguir el estado de tus postulaciones")
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tecnologías",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Kotlin y Jetpack Compose (Material 3)")
                    Text("• Navigation Compose")
                    Text("• ViewModel y StateFlow")
                    Text("• Room (base de datos local)")
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Créditos",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    integrantes.forEach { Text(text = it) }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Electiva Desarrollo de Aplicaciones Móviles",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Departamento de Telemática — Universidad del Cauca",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Versión 1.0",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
