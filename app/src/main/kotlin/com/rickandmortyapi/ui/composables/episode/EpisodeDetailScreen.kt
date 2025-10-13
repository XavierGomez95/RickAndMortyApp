package com.rickandmortyapi.ui.composables.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmortyapi.R
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.ui.colors.CharacterStatus
import com.rickandmortyapi.ui.composables.FailureMessage
import com.rickandmortyapi.ui.composables.LoadingSpinner
import com.rickandmortyapi.ui.viewmodel.EpisodeViewModel
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDetailNav(val id: Int)

@Composable
fun EpisodeDetailScreen(
    episodeId: Int,
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {
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
    ) {
        when (episodeResource) {
            Resource.Failure -> FailureMessage(messageError = stringResource(R.string.single_character_failure))
            is Resource.Success -> {
                val episode = episodeResource.data
                Column(
                    modifier = Modifier
                        .verticalScroll(
                            enabled = true,
                            state = rememberScrollState(),
                            reverseScrolling = false
                        )
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 80.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = episode.name,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        }

                        Spacer(Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .width(300.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Status:",
                                color = CharacterStatus.getColor(episode.airDate),
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Row (
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = episode.episode,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        Spacer(Modifier.padding(4.dp))

                        Row(
                            modifier = Modifier
                                .width(300.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Gender:",
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )

                            Row (
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = episode.created,
                                    fontSize = 16.sp,
                                )
                            }
                        }

                        Spacer(Modifier.padding(4.dp))

                        Row(
                            modifier = Modifier
                                .width(300.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Species:",
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )

                            /*Row (
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LazyColumn (
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    items(episode.characters) { character ->
                                        Text(
                                            text = character,
                                            fontSize = 16.sp,
                                        )
                                    }
                                }
                            }*/
                        }
                    }
                }
            }
            else -> LoadingSpinner()
        }
    }
}