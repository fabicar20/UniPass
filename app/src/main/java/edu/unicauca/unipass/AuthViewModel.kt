package edu.unicauca.unipass

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.security.MessageDigest

/**
 * Datos de la persona que inició sesión.
 */
data class Sesion(
    val id: Int,
    val nombre: String,
    val correo: String
)

/**
 * Registro e inicio de sesión contra la tabla "usuarios" de Room.
 * Solo entra quien se haya registrado antes con ese correo y esa contraseña.
 */
class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = UniPassDatabase.obtener(app).usuarioDao()

    var sesion by mutableStateOf<Sesion?>(null)
        private set

    var cargando by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun limpiarError() {
        error = null
    }

    fun iniciarSesion(
        correo: String,
        clave: String,
        onExito: () -> Unit
    ) {
        viewModelScope.launch {
            cargando = true
            error = null

            val correoLimpio = correo.trim().lowercase()
            val usuario = dao.buscarPorCorreo(correoLimpio)

            if (usuario != null &&
                usuario.claveHash == hash(correoLimpio, clave)
            ) {
                sesion = Sesion(
                    usuario.id,
                    usuario.nombre,
                    usuario.correo
                )

                cargando = false
                onExito()
            } else {
                cargando = false
                error = "Correo o contraseña incorrectos."
            }
        }
    }

    fun registrar(
        nombre: String,
        correo: String,
        clave: String,
        onExito: () -> Unit
    ) {
        viewModelScope.launch {
            cargando = true
            error = null

            val correoLimpio = correo.trim().lowercase()

            if (dao.buscarPorCorreo(correoLimpio) != null) {
                cargando = false
                error = "Ya existe una cuenta con ese correo."
                return@launch
            }

            val nombreLimpio = nombre.trim()

            val nuevoId = dao.insertar(
                Usuario(
                    nombre = nombreLimpio,
                    correo = correoLimpio,
                    claveHash = hash(correoLimpio, clave)
                )
            ).toInt()

            sesion = Sesion(
                nuevoId,
                nombreLimpio,
                correoLimpio
            )

            cargando = false
            onExito()
        }
    }

    fun cerrarSesion() {
        sesion = null
        error = null
    }

    private fun hash(
        correo: String,
        clave: String
    ): String =
        MessageDigest.getInstance("SHA-256")
            .digest("$correo:$clave".toByteArray())
            .joinToString("") {
                "%02x".format(it)
            }
}