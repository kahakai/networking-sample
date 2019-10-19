package com.artnest.networkingsample.data.services

import com.artnest.networkingsample.data.response.CatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

private const val API_KEY = "e6c2896a-e697-4fcb-b624-98820a5504e8"

interface TheCatApiService {

    @GET("images/search")
    fun getCats(
        @Header("x-api-key") apiKey: String = API_KEY
    ): Call<List<CatResponse>>
}
