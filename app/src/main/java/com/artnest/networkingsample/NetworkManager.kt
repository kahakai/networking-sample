package com.artnest.networkingsample

import com.artnest.networkingsample.data.response.CatResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object NetworkManager {

    fun openUrlWithHttpURLConnection(httpUrl: String): String {
        val url = URL(httpUrl)
        val urlConnection = (url.openConnection() as HttpURLConnection).apply {
            readTimeout = 10000 // ms
            connectTimeout = 15000 // ms
            requestMethod = "GET"
            doInput = true
        }
        // Start the query
        urlConnection.connect()

        val inputStream = urlConnection.inputStream
        val length = inputStream.available()
        // Convert the InputStream into a String
        return read(inputStream, length)
    }

    private fun read(inputStream: InputStream, len: Int): String {
        return inputStream.readBytes().toString(Charsets.UTF_8)
    }

    fun openUrlWithOkHttp(url: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                response.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            e.localizedMessage!!
        }

        /*val call = client.newCall(request)
        try {
            val response = call.execute()
            val content = response.body!!.string()
            response.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }*/
    }

    fun openUrlWithOkHttpAsync(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                val content = response.body!!.string()
                val moshi: Moshi = Moshi.Builder().build()
                val catAdapter: JsonAdapter<CatResponse> = moshi
                    .adapter(CatResponse::class.java)
                val cat: CatResponse = catAdapter.fromJson(content)!!
            }
        })
    }
}
