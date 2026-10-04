package edu.unicauca.unipass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    // Texto de búsqueda + ubicación + modalidad + tipo se combinan entre sí.

    val ofertas: StateFlow<List<Oferta>> =
        combine(
            _consulta.flatMapLatest { repo.ofertas(it) },
            _ubicacionSeleccionada,
            _modalidadSeleccionada,
            _tipoSeleccionado
        ) { lista, ubicacion, modalidad, tipo ->

            lista.filter { oferta ->

                (ubicacion == null || oferta.ubicacion == ubicacion) &&
                        (modalidad == null || oferta.modalidad == modalidad) &&
                        (tipo == null || oferta.tipo == tipo)
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

    // ---------- Filtros (cada uno es independiente) ----------

    fun filtrarPorUbicacion(ubicacion: String?) {
        _ubicacionSeleccionada.value = ubicacion
    }

    fun filtrarPorModalidad(modalidad: String?) {
        _modalidadSeleccionada.value = modalidad
    }

    fun filtrarPorTipo(tipo: String?) {
        _tipoSeleccionado.value = tipo
    }

    // ---------- Limpiar filtros ----------

    fun limpiarFiltros() {
        _ubicacionSeleccionada.value = null
        _modalidadSeleccionada.value = null
        _tipoSeleccionado.value = null
    }

    // ---------- Detalle ----------

    fun ofertaPorId(id: Int): kotlinx.coroutines.flow.Flow<Oferta?> =
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