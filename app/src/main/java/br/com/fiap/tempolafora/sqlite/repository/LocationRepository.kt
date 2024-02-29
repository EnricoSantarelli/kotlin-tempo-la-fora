package br.com.fiap.tempolafora.sqlite.repository

import android.content.Context
import br.com.fiap.tempolafora.sqlite.db.LocationDb
import br.com.fiap.tempolafora.sqlite.model.LocationSQL

class LocationRepository(context: Context) {
    private val db = LocationDb.getDatabase(context).locationDao()

    fun addLocation(location: LocationSQL): Unit {
        return db.addLocation(location)
    }

    fun getAllLocations(): List<LocationSQL> {
        return db.getAllLocations()
    }
}