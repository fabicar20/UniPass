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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class OfertasViewModel(app: Application) : AndroidViewModel(app) {

    private val repo =
        OfertaRepository(UniPassDatabase.obtener(app).ofertaDao())

    private val usuarioDao =
        UniPassDatabase.obtener(app).ofertaUsuarioDao()

    // ---------- Usuario actual ----------
    // Guardadas y postulaciones son de cada usuario,
    // no de todo el dispositivo.

    private val _usuarioId = MutableStateFlow<Int?>(null)

    fun fijarUsuario(id: Int?) {
        _usuarioId.value = id
    }

    private val interacciones: Flow<Map<Int, OfertaUsuario>> =
        _usuarioId.flatMapLatest { id ->
            if (id == null) {
                flowOf(emptyMap())
            } else {
                usuarioDao.observarDeUsuario(id)
                    .map { lista ->
                        lista.associateBy { it.ofertaId }
                    }
            }
        }

    /**
     * Completa "guardada" y "estadoPostulacion"
     * con lo que hizo el usuario actual.
     */
    private fun Oferta.conEstado(
        item: OfertaUsuario?
    ): Oferta =
        copy(
            guardada = item?.guardada ?: false,
            estadoPostulacion = item?.estadoPostulacion
        )

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
    // Texto de búsqueda + ubicación + modalidad + tipo
    // se combinan entre sí.

    val ofertas: StateFlow<List<Oferta>> =
        combine(
            _consulta.flatMapLatest { repo.ofertas(it) },
            interacciones,
            _ubicacionSeleccionada,
            _modalidadSeleccionada,
            _tipoSeleccionado
        ) { lista, mapa, ubicacion, modalidad, tipo ->

            lista
                .map { it.conEstado(mapa[it.id]) }
                .filter { oferta ->

                    (ubicacion == null ||
                            oferta.ubicacion == ubicacion) &&

                            (modalidad == null ||
                                    oferta.modalidad == modalidad) &&

                            (tipo == null ||
                                    oferta.tipo == tipo)
                }
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    // ---------- Guardadas del usuario actual ----------

    val guardadas: StateFlow<List<Oferta>> =
        combine(
            repo.ofertas(""),
            interacciones
        ) { lista, mapa ->

            lista
                .map { it.conEstado(mapa[it.id]) }
                .filter { it.guardada }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // ---------- Postulaciones del usuario actual ----------

    val postuladas: StateFlow<List<Oferta>> =
        combine(
            repo.ofertas(""),
            interacciones
        ) { lista, mapa ->

            lista
                .map { it.conEstado(mapa[it.id]) }
                .filter {
                    it.estadoPostulacion != null
                }

        }.stateIn(
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

    // ---------- Filtros ----------
    // Cada filtro es independiente y se pueden combinar.

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

    fun ofertaPorId(id: Int): Flow<Oferta?> =
        combine(
            repo.ofertaPorId(id),
            interacciones
        ) { oferta, mapa ->

            oferta?.conEstado(
                mapa[oferta.id]
            )
        }

    // ---------- Guardar oferta ----------

    fun alternarGuardada(oferta: Oferta) {

        val usuario = _usuarioId.value ?: return

        viewModelScope.launch {

            val actual =
                usuarioDao.buscar(usuario, oferta.id)
                    ?: OfertaUsuario(
                        usuarioId = usuario,
                        ofertaId = oferta.id
                    )

            usuarioDao.guardar(
                actual.copy(
                    guardada = !actual.guardada
                )
            )
        }
    }

    // ---------- Postularse ----------

    fun postular(id: Int) {

        val usuario = _usuarioId.value ?: return

        viewModelScope.launch {

            val actual =
                usuarioDao.buscar(usuario, id)
                    ?: OfertaUsuario(
                        usuarioId = usuario,
                        ofertaId = id
                    )

            usuarioDao.guardar(
                actual.copy(
                    estadoPostulacion = "Postulación enviada"
                )
            )
        }
    }
}