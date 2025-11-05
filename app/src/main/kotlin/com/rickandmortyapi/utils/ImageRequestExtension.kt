package com.rickandmortyapi.utils

import coil.request.ImageRequest
import com.rickandmortyapi.R

fun ImageRequest.Builder.applyDefaultCoilConfig(): ImageRequest.Builder {
    return this
        .placeholder(R.drawable.image_not_available)
        .error(R.drawable.image_not_available)
        .crossfade(true)
}