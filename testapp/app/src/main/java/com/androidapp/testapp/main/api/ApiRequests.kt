package com.androidapp.testapp.main.api

import com.androidapp.testapp.main.api.model.CatJson
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    @GET("/v1/images/search")
    fun getCatPicture(): Call<CatJson>
}