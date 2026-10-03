package edu.unicauca.unipass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPostulaciones(
    viewModel: OfertasViewModel,
    onOfertaClick: (Int) -> Unit = {}
) {

    val postuladas by viewModel.postuladas.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(text = "Mis postulaciones", fontSize = 26.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Aquí puedes consultar el estado de tus postulaciones.",
                fontSize = 16.sp
            )
        }

        if (postuladas.isEmpty()) {
            item {
                Text(
                    text = "Aún no te has postulado a ninguna oferta.\n" +
                            "Abre una oferta y toca \"Postularme ahora\".",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(postuladas, key = { it.id }) { oferta ->
            OfertaCard(
                oferta = oferta,
                onClick = { onOfertaClick(oferta.id) }
            ) {
                Text(
                    text = "Estado: ${oferta.estadoPostulacion}",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
