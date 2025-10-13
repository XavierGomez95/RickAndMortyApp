package com.rickandmortyapi.ui.composables.generic_list_container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rickandmortyapi.ui.composables.AppSearchField
import com.rickandmortyapi.ui.composables.generic_card.GenericCard
import com.rickandmortyapi.R

@Composable
fun <T> SearchListContainer(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit,
    list: List<T>,
    onItemClick: (T) -> Unit
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
            placeholder = stringResource(R.string.search_bar_placeholder)
        )

        Spacer(Modifier.height(4.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(list) { item ->
                GenericCard(
                    genericModel = item,
                    onClick = onItemClick
                )
            }
        }
    }
}