package com.rickandmortyapi.data.utils

sealed class Resource<out R> {
    data class Success<out R>(
        val result: R
    ): Resource<R>()

    // Singleton
    object Failure: Resource<Nothing>() // If need, create a data class to pass by parameters the Exception
    object Loading: Resource<Nothing>()
    object Init : Resource<Nothing>() // Used as initial state
}