package com.artnest.networkingsample

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object NetworkManager {

    fun connectToUrl(httpUrl: String): String {
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
}
