package edu.unicauca.unipass

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OfertaDao {

    // ---------- Lectura (Flow: la UI se actualiza sola) ----------

    @Query("SELECT * FROM ofertas ORDER BY id DESC")
    fun observarTodas(): Flow<List<Oferta>>

    @Query(
        """
        SELECT * FROM ofertas
        WHERE titulo LIKE '%' || :texto || '%'
           OR empresa LIKE '%' || :texto || '%'
           OR ubicacion LIKE '%' || :texto || '%'
        ORDER BY id DESC
        """
    )
    fun buscar(texto: String): Flow<List<Oferta>>

    @Query("SELECT * FROM ofertas WHERE id = :id")
    fun observarPorId(id: Int): Flow<Oferta?>

    @Query("SELECT * FROM ofertas WHERE guardada = 1 ORDER BY id DESC")
    fun observarGuardadas(): Flow<List<Oferta>>

    @Query("SELECT * FROM ofertas WHERE estadoPostulacion IS NOT NULL ORDER BY id DESC")
    fun observarPostuladas(): Flow<List<Oferta>>

    @Query("SELECT COUNT(*) FROM ofertas")
    suspend fun contar(): Int

    // ---------- Escritura ----------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(oferta: Oferta): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(ofertas: List<Oferta>)

    @Query("UPDATE ofertas SET guardada = :guardada WHERE id = :id")
    suspend fun actualizarGuardada(id: Int, guardada: Boolean)

    @Query("UPDATE ofertas SET estadoPostulacion = :estado WHERE id = :id")
    suspend fun actualizarEstadoPostulacion(id: Int, estado: String?)

    @Query("DELETE FROM ofertas WHERE id = :id")
    suspend fun eliminar(id: Int)
}