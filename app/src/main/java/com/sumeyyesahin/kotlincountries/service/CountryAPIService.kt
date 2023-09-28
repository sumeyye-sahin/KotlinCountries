package com.sumeyyesahin.kotlincountries.service

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sumeyyesahin.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {

    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //BASE_URL -> https://raw.githubusercontent.com/

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL) //base url is the url that we will use to get the data
        .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory is used to convert the data to gson
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //RxJava2CallAdapterFactory is used to convert the data to observable
        .build()//build the retrofit
        .create(CountryAPI::class.java)//create the api

    fun getData(): Single<List<Country>> {
        return api.getCountries()
    }
}