package com.newton.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(
    val page: Int,
    val results: T,
    val total_pages: Int,
    val total_results: Int
)
