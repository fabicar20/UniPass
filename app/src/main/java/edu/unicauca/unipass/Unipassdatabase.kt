package edu.unicauca.unipass

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Oferta::class,
        Usuario::class,
        OfertaUsuario::class,
        PerfilUsuario::class
    ],
    version = 5,
    exportSchema = false
)
abstract class UniPassDatabase : RoomDatabase() {

    abstract fun ofertaDao(): OfertaDao

    abstract fun usuarioDao(): UsuarioDao

    abstract fun ofertaUsuarioDao(): OfertaUsuarioDao
    abstract fun perfilUsuarioDao(): PerfilUsuarioDao

    companion object {

        @Volatile
        private var INSTANCIA: UniPassDatabase? = null

        fun obtener(context: Context): UniPassDatabase {
            return INSTANCIA ?: synchronized(this) {

                INSTANCIA ?: Room.databaseBuilder(
                    context.applicationContext,
                    UniPassDatabase::class.java,
                    "unipass.db"
                )
                    // Mientras desarrollas: si cambias la entidad, recrea la BD.
                    // Quítalo cuando tengas datos reales y escribe migraciones.
                    .fallbackToDestructiveMigration(
                        dropAllTables = true
                    )
                    .build()
                    .also {
                        INSTANCIA = it
                    }
            }
        }
    }
}