package edu.unicauca.unipass

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPerfil(
    nombre: String = "",
    correo: String = "",
    perfil: PerfilUsuario? = null,
    onEditar: () -> Unit = {},
    onAcerca: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {

    // Porcentaje de campos que el usuario ya llenó
    val campos = listOf(
        perfil?.universidad,
        perfil?.carrera,
        perfil?.semestre,
        perfil?.habilidades,
        perfil?.idiomas,
        perfil?.sobreMi
    )

    val porcentaje =
        campos.count { !it.isNullOrBlank() } * 100 / campos.size

    val listaHabilidades = (perfil?.habilidades ?: "")
        .split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    val listaIdiomas = (perfil?.idiomas ?: "")
        .lines()
        .filter { it.isNotBlank() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ---------- DATOS PRINCIPALES ----------
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

            if (!perfil?.universidad.isNullOrBlank()) {
                Text(text = perfil?.universidad ?: "")
            }

            if (!perfil?.carrera.isNullOrBlank()) {
                Text(text = perfil?.carrera ?: "")
            }

            if (!perfil?.semestre.isNullOrBlank()) {
                Text(text = "Semestre ${perfil?.semestre}")
            }
        }

        // ---------- PERFIL COMPLETADO ----------
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
                        text = "$porcentaje% completado",
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Completa tu información para encontrar oportunidades más compatibles."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onEditar,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (porcentaje == 0) {
                                "Completar perfil"
                            } else {
                                "Editar perfil"
                            }
                        )
                    }
                }
            }
        }

        // ---------- SOBRE MÍ ----------
        if (!perfil?.sobreMi.isNullOrBlank()) {

            item {

                Text(
                    text = "Sobre mí",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = perfil?.sobreMi ?: ""
                )
            }
        }

        // ---------- HABILIDADES ----------
        item {

            Text(
                text = "Habilidades",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (listaHabilidades.isEmpty()) {

                Text(
                    text = "Aún no has agregado habilidades."
                )

            } else {

                Row(
                    modifier = Modifier.horizontalScroll(
                        rememberScrollState()
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    listaHabilidades.forEach { habilidad ->

                        AssistChip(
                            onClick = {},
                            label = {
                                Text(habilidad)
                            }
                        )
                    }
                }
            }
        }

        // ---------- IDIOMAS ----------
        item {

            Text(
                text = "Idiomas",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (listaIdiomas.isEmpty()) {

                Text(
                    text = "Aún no has agregado idiomas."
                )

            } else {

                listaIdiomas.forEach { idioma ->

                    Text(
                        text = idioma
                    )
                }
            }
        }

        // ---------- DOCUMENTOS ----------
        item {

            Text(
                text = "Documentos",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Próximamente podrás subir tus documentos para adjuntarlos a tus postulaciones.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                DocumentoItem("Hoja de vida")

                DocumentoItem("Certificados")

                DocumentoItem("Diplomas")
            }
        }

        // ---------- BOTONES ----------
        item {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedButton(
                onClick = onAcerca,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Acerca de UniPass y créditos")
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}

@Composable
private fun DocumentoItem(titulo: String) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Sin archivo",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedButton(
                onClick = {
                    // Aún no funciona.
                    // Se implementará después.
                }
            ) {

                Icon(
                    imageVector = Icons.Default.UploadFile,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Subir")
            }
        }
    }
}