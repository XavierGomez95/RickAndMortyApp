package com.rickandmortyapi.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rickandmortyapi.ui.theme.LocalSemanticColors


@Composable
fun FailureMessage (
    modifier: Modifier = Modifier,
    messageError: String = "An unexpected error has occurred.",
    alignment: Alignment = Alignment.Center
) {
    val colors = LocalSemanticColors.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {
        Text(
            text = messageError,
            color = colors.error
        )
    }
}