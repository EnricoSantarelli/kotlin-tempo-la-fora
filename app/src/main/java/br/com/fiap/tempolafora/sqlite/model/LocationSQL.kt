package br.com.fiap.tempolafora.sqlite.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_location", indices = [Index(value = ["city"], unique = true)])
data class LocationSQL(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val city: String = "",
    val country: String = ""
)
