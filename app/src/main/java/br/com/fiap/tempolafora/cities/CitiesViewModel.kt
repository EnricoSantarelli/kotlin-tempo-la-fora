package br.com.fiap.tempolafora.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.tempolafora.sqlite.model.LocationSQL

class CitiesViewModel : ViewModel() {
    private val _cities = MutableLiveData<List<LocationSQL>>()
    val cities : LiveData<List<LocationSQL>> = _cities

    fun setCities(value: List<LocationSQL>){
        _cities.value = value
    }
}