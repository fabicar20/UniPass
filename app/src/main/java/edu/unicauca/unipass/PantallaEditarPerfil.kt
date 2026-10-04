package edu.unicauca.unipass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PantallaEditarPerfil(
    perfil: PerfilUsuario?,
    onGuardar: (
        universidad: String,
        carrera: String,
        semestre: String,
        habilidades: String,
        idiomas: String,
        sobreMi: String
    ) -> Unit,
    onCancelar: () -> Unit
) {
    var universidad by remember(perfil) { mutableStateOf(perfil?.universidad ?: "") }
    var carrera by remember(perfil) { mutableStateOf(perfil?.carrera ?: "") }
    var semestre by remember(perfil) { mutableStateOf(perfil?.semestre ?: "") }
    var habilidades by remember(perfil) { mutableStateOf(perfil?.habilidades ?: "") }
    var idiomas by remember(perfil) { mutableStateOf(perfil?.idiomas ?: "") }
    var sobreMi by remember(perfil) { mutableStateOf(perfil?.sobreMi ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Editar perfil",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = universidad,
            onValueChange = { universidad = it },
            label = { Text("Universidad") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = carrera,
            onValueChange = { carrera = it },
            label = { Text("Carrera") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = semestre,
            onValueChange = { semestre = it.filter { c -> c.isDigit() }.take(2) },
            label = { Text("Semestre") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = habilidades,
            onValueChange = { habilidades = it },
            label = { Text("Habilidades (separadas por comas)") },
            placeholder = { Text("Kotlin, Redes, Excel") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = idiomas,
            onValueChange = { idiomas = it },
            label = { Text("Idiomas (uno por línea)") },
            placeholder = { Text("Español — Nativo") },
            minLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sobreMi,
            onValueChange = { sobreMi = it },
            label = { Text("Sobre mí") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                onGuardar(universidad, carrera, semestre, habilidades, idiomas, sobreMi)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}