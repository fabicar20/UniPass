package edu.unicauca.unipass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaDetalle(
    id: Int,
    viewModel: OfertasViewModel,
    onVolver: () -> Unit = {}
) {

    // remember(id): evita crear un Flow nuevo en cada recomposición
    val flujo = remember(id) { viewModel.ofertaPorId(id) }
    val oferta by flujo.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onVolver) {
            Text("← Volver")
        }

        val o = oferta
        if (o == null) {
            Text("Cargando oferta...")
            return@Column
        }

        Text(text = o.titulo, fontSize = 26.sp)

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = o.empresa, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(onClick = {}, label = { Text(o.tipo) })
            AssistChip(onClick = {}, label = { Text(o.modalidad) })
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = if (o.validaRequisitoGrado)
                        "✓ Válida para requisito de grado"
                    else
                        "No válida para requisito de grado",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "📍 Ubicación", fontSize = 16.sp)
                Text(text = o.ubicacion)

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "⏱ Duración", fontSize = 16.sp)
                Text(text = o.duracion)

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "🕒 Horario", fontSize = 16.sp)
                Text(text = o.horario)

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "💰 Remuneración", fontSize = 16.sp)
                Text(text = o.remuneracion)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Responsabilidades", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        o.listaResponsabilidades.forEach { Text(text = "• $it") }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Requisitos", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        o.listaRequisitos.forEach { Text(text = "• $it") }

        Spacer(modifier = Modifier.height(24.dp))

        // ---------- POSTULARME ----------
        val yaPostulado = o.estadoPostulacion != null

        Button(
            onClick = { viewModel.postular(o.id) },
            enabled = !yaPostulado,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (yaPostulado) "✓ Ya te postulaste (${o.estadoPostulacion})"
                else "Postularme ahora"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ---------- GUARDAR ----------
        OutlinedButton(
            onClick = { viewModel.alternarGuardada(o) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (o.guardada) Icons.Default.Bookmark
                else Icons.Default.BookmarkBorder,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (o.guardada) "Quitar de guardados" else "Guardar oferta")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}