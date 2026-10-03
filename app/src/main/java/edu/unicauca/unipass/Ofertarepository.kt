package edu.unicauca.unipass

import kotlinx.coroutines.flow.Flow

class OfertaRepository(private val dao: OfertaDao) {

    fun ofertas(texto: String = ""): Flow<List<Oferta>> =
        if (texto.isBlank()) dao.observarTodas() else dao.buscar(texto.trim())

    fun ofertaPorId(id: Int): Flow<Oferta?> = dao.observarPorId(id)

    val guardadas: Flow<List<Oferta>> = dao.observarGuardadas()

    val postuladas: Flow<List<Oferta>> = dao.observarPostuladas()

    suspend fun alternarGuardada(oferta: Oferta) =
        dao.actualizarGuardada(oferta.id, !oferta.guardada)

    suspend fun postular(id: Int) =
        dao.actualizarEstadoPostulacion(id, "Postulación enviada")

    suspend fun cambiarEstado(id: Int, estado: String?) =
        dao.actualizarEstadoPostulacion(id, estado)

    /** Inserta los datos de ejemplo solo la primera vez (BD vacía). */
    suspend fun sembrarSiVacia() {
        if (dao.contar() == 0) dao.insertarTodas(DatosIniciales.ofertas)
    }
}

/** Datos de ejemplo. Todas empiezan sin guardar y sin postular. */
object DatosIniciales {
    val ofertas = listOf(
        Oferta(
            titulo = "Practicante de Desarrollo de Software",
            empresa = "Empresa Tech",
            ubicacion = "Popayán, Cauca",
            modalidad = "Presencial",
            tipo = "Pasantía",
            remuneracion = "$1.200.000",
            duracion = "6 meses",
            horario = "Tiempo completo",
            responsabilidades = "Desarrollar funcionalidades\nProbar y depurar código\nApoyar al equipo",
            requisitos = "Estudiante universitario\nConocimientos de Kotlin o Java\nGit básico"
        ),
        Oferta(
            titulo = "Practicante de Redes y Telecomunicaciones",
            empresa = "Telecomunicaciones del Cauca",
            ubicacion = "Popayán, Cauca",
            modalidad = "Presencial",
            tipo = "Pasantía",
            remuneracion = "$1.100.000",
            duracion = "6 meses",
            horario = "Medio tiempo",
            responsabilidades = "Configurar equipos de red\nMonitorear la red\nSoporte técnico",
            requisitos = "Estudiante de Telemática o afines\nConocimientos de redes"
        ),
        Oferta(
            titulo = "Analista de Datos Junior",
            empresa = "Data Solutions",
            ubicacion = "Remoto",
            modalidad = "Remoto",
            tipo = "Pasantía",
            remuneracion = "$1.300.000",
            duracion = "6 meses",
            horario = "Medio tiempo",
            responsabilidades = "Analizar datos\nCrear reportes\nApoyar al equipo",
            requisitos = "Estudiante universitario\nConocimientos de Excel\nManejo básico de Python"
        ),
        Oferta(
            titulo = "Desarrollador Android Junior",
            empresa = "App Studio",
            ubicacion = "Remoto",
            modalidad = "Remoto",
            tipo = "Pasantía",
            remuneracion = "$1.400.000",
            duracion = "4 meses",
            horario = "Medio tiempo",
            responsabilidades = "Construir pantallas con Jetpack Compose\nConsumir servicios web\nEscribir pruebas",
            requisitos = "Kotlin básico\nConocimientos de Compose"
        ),
        Oferta(
            titulo = "Auxiliar de Soporte Técnico TI",
            empresa = "Cauca Soluciones",
            ubicacion = "Popayán, Cauca",
            modalidad = "Híbrido",
            tipo = "Empleo",
            remuneracion = "$1.500.000",
            duracion = "Indefinido",
            horario = "Tiempo completo",
            responsabilidades = "Atender solicitudes de soporte\nMantener equipos de cómputo",
            requisitos = "Tecnólogo o estudiante de últimos semestres\nBuena atención al cliente",
            validaRequisitoGrado = false
        )
    )
}

