package com.example.moviesappxml.common.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("status_code")
    val statusCode: Int? = null,
    @SerialName("status_message")
    val statusMessage: String? = null,
    @SerialName("success")
    val success: Boolean? = null
)