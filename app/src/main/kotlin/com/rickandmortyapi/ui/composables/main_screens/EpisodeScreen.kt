package com.rickandmortyapi.ui.composables.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rickandmortyapi.R
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.ui.composables.FailureMessage
import com.rickandmortyapi.ui.composables.LoadingSpinner
import com.rickandmortyapi.ui.viewmodel.EpisodeViewModel

@Composable
fun EpisodeScreen (
    navController: NavController,
    modifier: Modifier,
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {
    val episodesResource = episodeViewModel.episodes.collectAsState().value

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

            Column(
                modifier = modifier
                    .padding(12.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 16.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(allEpisodes) { episode ->
                        /*GenericCard(
                            genericModel = episode,
                            // TODO
                            onClick = { selectedItem ->
                                navController.navigate(EpisodeDetailNav(selectedItem))
                            }
                        )*/
                    }
                }
            }
        }

    }
}