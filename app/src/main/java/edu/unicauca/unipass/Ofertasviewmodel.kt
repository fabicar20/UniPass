package edu.unicauca.unipass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine

@OptIn(ExperimentalCoroutinesApi::class)
class OfertasViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = OfertaRepository(UniPassDatabase.obtener(app).ofertaDao())

    private val _consulta = MutableStateFlow("")
    val consulta: StateFlow<String> = _consulta

    private val _ubicacionSeleccionada = MutableStateFlow<String?>(null)
    val ubicacionSeleccionada: StateFlow<String?> = _ubicacionSeleccionada

    /** Lista filtrada por el texto de búsqueda. */
    val ofertas: StateFlow<List<Oferta>> =
        combine(_consulta, _ubicacionSeleccionada) { texto, ubicacion ->
            texto to ubicacion
        }
            .flatMapLatest { (texto, ubicacion) ->
                if (ubicacion == null) {
                    repo.ofertas(texto)
                } else {
                    repo.ofertasPorUbicacion(ubicacion)
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    val guardadas: StateFlow<List<Oferta>> = repo.guardadas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val postuladas: StateFlow<List<Oferta>> = repo.postuladas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { repo.sembrarSiVacia() }
    }

    fun buscar(texto: String) { _consulta.value = texto }

    fun filtrarPorUbicacion(ubicacion: String?) {
        _ubicacionSeleccionada.value = ubicacion
    }

    fun ofertaPorId(id: Int): Flow<Oferta?> = repo.ofertaPorId(id)

    fun alternarGuardada(oferta: Oferta) {
        viewModelScope.launch { repo.alternarGuardada(oferta) }
    }

    fun postular(id: Int) {
        viewModelScope.launch { repo.postular(id) }
    }
}