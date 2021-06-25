package com.androidapp.testapp.main.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class CatsRequest(private val updateMainActivity: (String) -> Unit) {

    private val url = "https://api.thecatapi.com"
    private var imageLoadingCount = 10

    fun getCatPics() {
        for (i in 1..imageLoadingCount) {
            val api = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val response  = api.getCatPicture().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!

                    withContext(Dispatchers.Main) {
                        updateMainActivity(data[0].url)
                    }
                }
            }
        }
    }
}