package com.rickandmortyapi.data.model.repository

data class ApiResponse<T>( // Generic API response data class
    val info: ApiResponseInfo,
    val results: List<T> // Generic Collection (Character, Episode)
)
