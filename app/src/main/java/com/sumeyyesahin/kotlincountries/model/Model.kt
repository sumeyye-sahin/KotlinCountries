package com.sumeyyesahin.kotlincountries.model

import com.google.gson.annotations.SerializedName


data class Country( //data class is a class that is used to hold data/state

    @SerializedName("name")
    val countryName: String?,

    @SerializedName("capital")
    val countryCapital: String?,

    @SerializedName("currency")
    val countryCurrency: String?,

    @SerializedName("language")
    val countryLanguage: String?,

    @SerializedName("region")
    val countryRegion: String?,

    @SerializedName("flag")
    val countryFlag: String?
    )

