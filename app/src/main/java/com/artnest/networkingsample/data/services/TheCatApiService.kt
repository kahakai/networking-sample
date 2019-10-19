package com.artnest.networkingsample.data.services

import com.artnest.networkingsample.data.response.CatResponse
import retrofit2.Call
import retrofit2.http.GET

interface TheCatApiService {

    @GET("images/search")
    fun getCats(): Call<List<CatResponse>>
}
