package com.sumeyyesahin.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumeyyesahin.kotlincountries.model.Country
import com.sumeyyesahin.kotlincountries.service.CountryAPIService
import io.reactivex.disposables.CompositeDisposable

class FeedViewModel : ViewModel() {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable() //CompositeDisposable is used to dispose the observable

    val countries= MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData(){
        getDataFromAPI()

    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread()) //subscribeOn is used to get the data from another thread
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()) //observeOn is used to observe the data on the main thread
                .subscribeWith(object : io.reactivex.observers.DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        countries.value = t
                        countryError.value = false
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                    }

                })
        )
    }
}