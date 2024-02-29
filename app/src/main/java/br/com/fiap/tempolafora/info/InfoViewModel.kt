package br.com.fiap.tempolafora.info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.tempolafora.models.Location
import br.com.fiap.tempolafora.models.Weather

class InfoViewModel : ViewModel() {
    private val _weather = MutableLiveData<Weather>()
    val weather : LiveData<Weather> = _weather

    fun setWeather(value: Weather?){
        if(value != null) {
            _weather.value = value!!
        }
    }

    private val _location = MutableLiveData<Location>()
    val location : LiveData<Location> = _location

    fun setLocation(value: Location?){
        if(value != null) {
            _location.value = value!!
        }
    }

    private val _wasCityAdd = MutableLiveData<Boolean>()
    val wasCityAdd : LiveData<Boolean> = _wasCityAdd

    fun setWasCityAdd(value: Boolean?){
        _wasCityAdd.value = value!!
    }

    private val _cityAlredyAdded = MutableLiveData<Boolean>()
    val cityAlredyAdded : LiveData<Boolean> = _cityAlredyAdded

    fun setCityAlreadyAdded(value: Boolean?){
        _cityAlredyAdded.value = value!!
    }

}