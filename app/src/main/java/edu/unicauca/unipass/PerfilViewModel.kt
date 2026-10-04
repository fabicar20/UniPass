package edu.unicauca.unipass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Carga y guarda el perfil del usuario que tiene la sesión abierta. */
@OptIn(ExperimentalCoroutinesApi::class)
class PerfilViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = UniPassDatabase.obtener(app).perfilUsuarioDao()

    private val _usuarioId = MutableStateFlow<Int?>(null)

    fun fijarUsuario(id: Int?) {
        _usuarioId.value = id
    }

    val perfil: StateFlow<PerfilUsuario?> =
        _usuarioId
            .flatMapLatest { id ->
                if (id == null) flowOf(null) else dao.observar(id)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                null
            )

    fun guardar(
        universidad: String,
        carrera: String,
        semestre: String,
        habilidades: String,
        idiomas: String,
        sobreMi: String
    ) {
        val usuario = _usuarioId.value ?: return
        viewModelScope.launch {
            dao.guardar(
                PerfilUsuario(
                    usuarioId = usuario,
                    universidad = universidad.trim(),
                    carrera = carrera.trim(),
                    semestre = semestre.trim(),
                    habilidades = habilidades.trim(),
                    idiomas = idiomas.trim(),
                    sobreMi = sobreMi.trim()
                )
            )
        }
    }
}