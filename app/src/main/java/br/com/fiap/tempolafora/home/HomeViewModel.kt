package br.com.fiap.tempolafora.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.tempolafora.models.Location

class HomeViewModel : ViewModel() {
    private val _cityName = MutableLiveData<String>()
    val cityName : LiveData<String> = _cityName

    fun setCityName(value: String){
        _cityName.value = value
    }

    private val _openAlertDialog = MutableLiveData<Boolean>()
    val openAlertDialog : LiveData<Boolean> = _openAlertDialog

    fun setOpenAlertDialog(value: Boolean){
        _openAlertDialog.value = value
    }

    private val _openErrorDialog = MutableLiveData<Boolean>()
    val openErrorDialog : LiveData<Boolean> = _openErrorDialog

    fun setOpenErrorDialog(value: Boolean){
        _openErrorDialog.value = value
    }

    private val _isTextFieldMissing = MutableLiveData<Boolean>()
    val isTextFieldMissing : LiveData<Boolean> = _isTextFieldMissing

    fun setIsTextFieldMissing(value: Boolean){
        _isTextFieldMissing.value = value
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    fun setErrorMessage(value: String){
        _errorMessage.value = value
    }

    private val _location = MutableLiveData<Location>()
    val location : LiveData<Location> = _location

    fun setLocation(value: Location?){
        if(value != null) {
            _location.value = value!!
        }
    }

}