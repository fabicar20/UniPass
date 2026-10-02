package edu.unicauca.unipass

import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.AssistChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PantallaDetalle(
    onVolver: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(
            onClick = {
                onVolver()
            }
        ) {
            Text("← Volver")
        }

        Text(
            text = "Analista de Datos Junior",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Data Solutions",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AssistChip(
                onClick = {},
                label = {
                    Text("Pasantía")
                }
            )

            AssistChip(
                onClick = {},
                label = {
                    Text("Remoto")
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "✓ Válida para requisito de grado",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = " Ubicación",
                    fontSize = 16.sp
                )

                Text(
                    text = "Remoto"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "⏱ Duración",
                    fontSize = 16.sp
                )

                Text(
                    text = "6 meses"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = " Horario",
                    fontSize = 16.sp
                )

                Text(
                    text = "Medio tiempo"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = " Remuneración",
                    fontSize = 16.sp
                )

                Text(
                    text = "$1.300.000"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Responsabilidades",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "• Analizar datos"
        )

        Text(
            text = "• Crear reportes"
        )

        Text(
            text = "• Apoyar al equipo"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Requisitos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "• Estudiante universitario"
        )

        Text(
            text = "• Conocimientos de Excel"
        )

        Text(
            text = "• Manejo básico de Python"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Postularme ahora")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar oferta")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}