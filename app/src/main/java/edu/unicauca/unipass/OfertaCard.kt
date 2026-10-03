package edu.unicauca.unipass

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Tarjeta reutilizable de una oferta. Reemplaza las tarjetas copiadas en cada pantalla. */
@Composable
fun OfertaCard(
    oferta: Oferta,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    pie: @Composable () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Text(
                text = oferta.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = oferta.empresa)
            Text(text = " ${oferta.ubicacion}")
            Text(text = " ${oferta.tipo} · ${oferta.modalidad}")

            Spacer(modifier = Modifier.height(6.dp))

            pie()
        }
    }
}