package edu.unicauca.unipass

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OfertaDaoTest {

    private lateinit var db: UniPassDatabase
    private lateinit var dao: OfertaDao

    private fun ofertaDe(titulo: String, empresa: String = "Empresa") = Oferta(
        titulo = titulo,
        empresa = empresa,
        ubicacion = "Popayán, Cauca",
        modalidad = "Presencial",
        tipo = "Pasantía",
        remuneracion = "$1.000.000"
    )

    @Before
    fun crearBaseEnMemoria() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UniPassDatabase::class.java).build()
        dao = db.ofertaDao()
    }

    @After
    fun cerrar() = db.close()

    @Test
    fun insertaYLee() = runBlocking {
        dao.insertar(ofertaDe("Practicante de Software"))

        val lista = dao.observarTodas().first()

        assertEquals(1, lista.size)
        assertEquals("Practicante de Software", lista[0].titulo)
    }

    @Test
    fun buscaPorTexto() = runBlocking {
        dao.insertar(ofertaDe("Analista de Datos", "Data Solutions"))
        dao.insertar(ofertaDe("Practicante de Redes", "Telco"))

        val resultado = dao.buscar("redes").first()

        assertEquals(1, resultado.size)
        assertEquals("Practicante de Redes", resultado[0].titulo)
    }

    @Test
    fun guardaYQuitaDeGuardados() = runBlocking {
        val id = dao.insertar(ofertaDe("Oferta A")).toInt()
        assertTrue(dao.observarGuardadas().first().isEmpty())

        dao.actualizarGuardada(id, true)
        assertEquals(1, dao.observarGuardadas().first().size)

        dao.actualizarGuardada(id, false)
        assertTrue(dao.observarGuardadas().first().isEmpty())
    }

    @Test
    fun postulaCambiaElEstado() = runBlocking {
        val id = dao.insertar(ofertaDe("Oferta B")).toInt()
        assertNull(dao.observarPorId(id).first()?.estadoPostulacion)

        dao.actualizarEstadoPostulacion(id, "Postulación enviada")

        val oferta = dao.observarPorId(id).first()
        assertNotNull(oferta)
        assertEquals("Postulación enviada", oferta!!.estadoPostulacion)
        assertEquals(1, dao.observarPostuladas().first().size)
    }

    @Test
    fun sembrarSoloInsertaUnaVez() = runBlocking {
        val repo = OfertaRepository(dao)

        repo.sembrarSiVacia()
        repo.sembrarSiVacia() // la segunda vez no debe duplicar

        assertEquals(DatosIniciales.ofertas.size, dao.contar())
    }
}
