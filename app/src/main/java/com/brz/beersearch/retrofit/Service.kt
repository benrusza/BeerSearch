package com.brz.beersearch.retrofit

import com.brz.beersearch.model.ResponseBeersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("beers")
    fun getBeers(
        @Query("beer_name") beerName : String
    ) : Call<List<ResponseBeersItem>>

    @GET("beers")
    fun getBeersById(
        @Query("ids") id : String
    ) : Call<List<ResponseBeersItem>>
}