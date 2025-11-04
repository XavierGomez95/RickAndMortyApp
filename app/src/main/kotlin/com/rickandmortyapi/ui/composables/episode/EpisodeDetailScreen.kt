package com.rickandmortyapi.ui.composables.episode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmortyapi.R
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.ui.composables.FailureMessage
import com.rickandmortyapi.ui.composables.LoadingSpinner
import com.rickandmortyapi.ui.theme.LocalSemanticColors
import com.rickandmortyapi.ui.viewmodel.EpisodeViewModel
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDetailNav(val id: Int)

@Composable
fun EpisodeDetailScreen(
    episodeId: Int,
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {
    val colors = LocalSemanticColors.current

    LaunchedEffect(episodeId) {
        val currentState = episodeViewModel.singleEpisode.value
        if (currentState is Resource.Init) {
            episodeViewModel.getEpisodeById(episodeId)
        }
    }

    val episodeResource = episodeViewModel.singleEpisode.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.customBackground),
        contentAlignment = Alignment.Center
    ) {
        when (episodeResource) {
            Resource.Failure -> FailureMessage(messageError = stringResource(R.string.single_episode_failure))
            is Resource.Success -> {
                val episode = episodeResource.data
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 80.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = episode.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = colors.episodeName,
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .width(300.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.episode_detail__date),
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.episodeDate,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = episode.uiDate ?: stringResource(R.string.unknown),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = colors.episodeDate,
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .width(300.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.episode_detail__season),
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.episodeSeason,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = episode.uiSeason ?: stringResource(R.string.unknown),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = colors.episodeSeason
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .width(300.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.episode_detail__episode),
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.episode,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = episode.uiEpisode
                                        ?: stringResource(R.string.unknown),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = colors.episode,
                                )
                            }
                        }
                    }
                }
            }
            else -> LoadingSpinner()
        }
    }
}