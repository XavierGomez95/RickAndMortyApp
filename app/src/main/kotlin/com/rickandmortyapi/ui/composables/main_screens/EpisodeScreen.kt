package com.rickandmortyapi.ui.composables.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rickandmortyapi.R
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.ui.composables.FailureMessage
import com.rickandmortyapi.ui.composables.LoadingSpinner
import com.rickandmortyapi.ui.composables.episode.EpisodeCard
import com.rickandmortyapi.ui.composables.episode.EpisodeDetailNav
import com.rickandmortyapi.ui.composables.generic_list_container.SearchListContainer
import com.rickandmortyapi.ui.viewmodel.EpisodeViewModel

@Composable
fun EpisodeScreen (
    navController: NavController,
    modifier: Modifier,
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {
    val episodesResource = episodeViewModel.episodes.collectAsState().value

    var searchBar by remember { mutableStateOf("") }
    var isSearchBarActive by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (episodesResource is Resource.Init) {
            episodeViewModel.fetchEpisodes()
        }
    }
    when (episodesResource) {
        is Resource.Init -> Unit
        is Resource.Failure -> FailureMessage(messageError = stringResource(R.string.episodes_failure))
        is Resource.Loading -> LoadingSpinner()
        is Resource.Success -> {
            val allEpisodes = episodesResource.data
            val filteredEpisodes = allEpisodes.filter { currentCharacter ->
                (searchBar.isBlank() || currentCharacter.name.contains(searchBar, ignoreCase = true))
            }

            SearchListContainer(
                modifier = modifier,
                query = searchBar,
                onQueryChange = { searchBar = it },
                isActive = isSearchBarActive,
                onActiveChange = { isSearchBarActive = it },
                placeholder = stringResource(R.string.search_bar_placeholder__episodes)

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredEpisodes) { episode ->
                        EpisodeCard(
                            episode = episode,
                            onClick = { episode ->
                                navController.navigate(EpisodeDetailNav(episode.id))
                            }
                        )
                    }
                }
            }
        }

    }
}