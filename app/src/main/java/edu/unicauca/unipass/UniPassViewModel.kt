package edu.unicauca.unipass

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UniPassViewModel : ViewModel() {

    var correo by mutableStateOf("")
        private set

    var contraseña by mutableStateOf("")
        private set

    fun actualizarCorreo(nuevoCorreo: String) {
        correo = nuevoCorreo
    }

    fun actualizarContraseña(nuevaContraseña: String) {
        contraseña = nuevaContraseña
    }
}