package br.com.fiap.tempolafora.sqlite.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.tempolafora.sqlite.dao.LocationDAO
import br.com.fiap.tempolafora.sqlite.model.LocationSQL

@Database(entities = [LocationSQL::class], version = 1)
abstract class LocationDb : RoomDatabase() {

    abstract fun locationDao() : LocationDAO

    companion object {

        private  lateinit var instance : LocationDb

        fun getDatabase(context: Context): LocationDb {
            if(!::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context,
                    LocationDb::class.java,
                    "location_db"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
}