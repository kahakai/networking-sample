package com.artnest.networkingsample.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CatResponse(
    @SerialName("id")
    val id: String,

    @SerialName("url")
    val imageUrl: String,

    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int
)
