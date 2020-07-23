package com.srikanth.androidcodingtestwithkotlin.retrofit

import com.srikanth.androidcodingtestwithkotlin.model.AboutCanadaModel
import retrofit2.Call
import retrofit2.http.GET

/**
 * This is an interface that contains methods that represent  API Call
 */

interface ApiService {
    @GET("facts.json")
    fun fetchAboutCanadaApi(): Call<AboutCanadaModel>
}