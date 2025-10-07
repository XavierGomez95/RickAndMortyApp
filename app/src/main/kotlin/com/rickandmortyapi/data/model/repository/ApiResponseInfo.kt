package com.rickandmortyapi.data.model.repository

data class ApiResponseInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
