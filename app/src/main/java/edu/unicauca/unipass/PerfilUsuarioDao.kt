package edu.unicauca.unipass

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilUsuarioDao {

    @Query("SELECT * FROM perfiles WHERE usuarioId = :usuarioId")
    fun observar(usuarioId: Int): Flow<PerfilUsuario?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(perfil: PerfilUsuario)
}