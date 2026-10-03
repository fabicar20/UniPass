package edu.unicauca.unipass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaGuardados(
    viewModel: OfertasViewModel,
    onOfertaClick: (Int) -> Unit = {}
) {

    val guardadas by viewModel.guardadas.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(text = "Ofertas guardadas", fontSize = 26.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Aquí encontrarás las oportunidades que guardaste.",
                fontSize = 16.sp
            )
        }

        if (guardadas.isEmpty()) {
            item {
                Text(
                    text = "Aún no has guardado ninguna oferta.\n" +
                            "Abre una oferta y toca \"Guardar oferta\".",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(guardadas, key = { it.id }) { oferta ->
            OfertaCard(
                oferta = oferta,
                onClick = { onOfertaClick(oferta.id) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🔖 Oferta guardada",
                        color = MaterialTheme.colorScheme.primary
                    )

                    TextButton(onClick = { viewModel.alternarGuardada(oferta) }) {
                        Text("Quitar")
                    }
                }
            }
        }
    }
}
