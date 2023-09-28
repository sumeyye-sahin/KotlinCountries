package com.sumeyyesahin.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumeyyesahin.kotlincountries.model.Country

class CountryViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(){
        countryLiveData.value= Country("Turkey","Ankara","TRY","Turkish","Asia","")
    }

}