package com.rickandmortyapi.ui.composables.generic_list_container

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rickandmortyapi.ui.composables.AppSearchField


@Composable
fun SearchListContainer(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit,
    placeholder: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        AppSearchField(
            modifier = modifier,
            query = query,
            onQueryChange = onQueryChange,
            isActive = isActive,
            onActiveChange = onActiveChange,
            placeholder = placeholder
        )

        Spacer(Modifier.height(4.dp))

        content()
    }
}