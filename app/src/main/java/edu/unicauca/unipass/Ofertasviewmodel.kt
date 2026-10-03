package edu.unicauca.unipass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class OfertasViewModel(app: Application) : AndroidViewModel(app) {

    private val repo =
        OfertaRepository(UniPassDatabase.obtener(app).ofertaDao())

    // ---------- Búsqueda ----------

    private val _consulta = MutableStateFlow("")
    val consulta: StateFlow<String> = _consulta

    // ---------- Filtros ----------

    private val _ubicacionSeleccionada =
        MutableStateFlow<String?>(null)

    val ubicacionSeleccionada: StateFlow<String?> =
        _ubicacionSeleccionada

    private val _modalidadSeleccionada =
        MutableStateFlow<String?>(null)

    val modalidadSeleccionada: StateFlow<String?> =
        _modalidadSeleccionada

    private val _tipoSeleccionado =
        MutableStateFlow<String?>(null)

    val tipoSeleccionado: StateFlow<String?> =
        _tipoSeleccionado

    // ---------- Lista de ofertas ----------

    val ofertas: StateFlow<List<Oferta>> =
        combine(
            _consulta,
            _ubicacionSeleccionada,
            _modalidadSeleccionada,
            _tipoSeleccionado
        ) { texto, ubicacion, modalidad, tipo ->
            FiltrosActuales(
                texto = texto,
                ubicacion = ubicacion,
                modalidad = modalidad,
                tipo = tipo
            )
        }
            .flatMapLatest { filtros ->

                when {
                    filtros.ubicacion != null -> {
                        repo.ofertasPorUbicacion(
                            filtros.ubicacion
                        )
                    }

                    filtros.modalidad != null -> {
                        repo.ofertasPorModalidad(
                            filtros.modalidad
                        )
                    }

                    filtros.tipo != null -> {
                        repo.ofertasPorTipo(
                            filtros.tipo
                        )
                    }

                    else -> {
                        repo.ofertas(filtros.texto)
                    }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    // ---------- Guardadas ----------

    val guardadas: StateFlow<List<Oferta>> =
        repo.guardadas.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // ---------- Postulaciones ----------

    val postuladas: StateFlow<List<Oferta>> =
        repo.postuladas.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // ---------- Inicialización ----------

    init {
        viewModelScope.launch {
            repo.sembrarSiVacia()
        }
    }

    // ---------- Búsqueda ----------

    fun buscar(texto: String) {
        _consulta.value = texto
    }

    // ---------- Filtro por ubicación ----------

    fun filtrarPorUbicacion(ubicacion: String?) {
        _ubicacionSeleccionada.value = ubicacion
        _modalidadSeleccionada.value = null
        _tipoSeleccionado.value = null
    }

    // ---------- Filtro por modalidad ----------

    fun filtrarPorModalidad(modalidad: String?) {
        _modalidadSeleccionada.value = modalidad
        _ubicacionSeleccionada.value = null
        _tipoSeleccionado.value = null
    }

    // ---------- Filtro por tipo ----------

    fun filtrarPorTipo(tipo: String?) {
        _tipoSeleccionado.value = tipo
        _ubicacionSeleccionada.value = null
        _modalidadSeleccionada.value = null
    }

    // ---------- Limpiar filtros ----------

    fun limpiarFiltros() {
        _ubicacionSeleccionada.value = null
        _modalidadSeleccionada.value = null
        _tipoSeleccionado.value = null
    }

    // ---------- Detalle ----------

    fun ofertaPorId(id: Int): Flow<Oferta?> =
        repo.ofertaPorId(id)

    // ---------- Guardar oferta ----------

    fun alternarGuardada(oferta: Oferta) {
        viewModelScope.launch {
            repo.alternarGuardada(oferta)
        }
    }

    // ---------- Postularse ----------

    fun postular(id: Int) {
        viewModelScope.launch {
            repo.postular(id)
        }
    }
}

// ---------- Estado actual de los filtros ----------

private data class FiltrosActuales(
    val texto: String,
    val ubicacion: String?,
    val modalidad: String?,
    val tipo: String?
)