package edu.unicauca.unipass

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OfertaUsuarioDao {

    @Query("SELECT * FROM ofertas_usuario WHERE usuarioId = :usuarioId")
    fun observarDeUsuario(usuarioId: Int): Flow<List<OfertaUsuario>>

    @Query(
        "SELECT * FROM ofertas_usuario " +
                "WHERE usuarioId = :usuarioId AND ofertaId = :ofertaId LIMIT 1"
    )
    suspend fun buscar(
        usuarioId: Int,
        ofertaId: Int
    ): OfertaUsuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(item: OfertaUsuario)
}