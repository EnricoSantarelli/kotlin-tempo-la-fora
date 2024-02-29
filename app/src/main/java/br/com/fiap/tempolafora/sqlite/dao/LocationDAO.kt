package br.com.fiap.tempolafora.sqlite.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.tempolafora.sqlite.model.LocationSQL

@Dao
interface LocationDAO {

    @Insert
    fun addLocation(location: LocationSQL): Unit

    @Query("SELECT * FROM tbl_location")
    fun getAllLocations(): List<LocationSQL>
}