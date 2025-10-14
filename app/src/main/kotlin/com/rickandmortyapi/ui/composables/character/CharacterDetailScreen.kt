package com.rickandmortyapi.ui.composables.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rickandmortyapi.R
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.ui.colors.CharacterStatus
import com.rickandmortyapi.ui.composables.FailureMessage
import com.rickandmortyapi.ui.composables.LoadingSpinner
import com.rickandmortyapi.ui.viewmodel.CharacterViewModel
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailNav(val id: Int)

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    characterViewModel: CharacterViewModel = hiltViewModel()
) {
    LaunchedEffect(characterId) {
        val currentState = characterViewModel.singleCharacterStateFlow.value
        if (currentState is Resource.Init) {
            characterViewModel.getCharacterById(characterId)
        }
    }

    val characterResource = characterViewModel.singleCharacterStateFlow.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (characterResource) {
            Resource.Failure -> FailureMessage(messageError = stringResource(R.string.single_character_failure))
            is Resource.Success -> {
                val character = characterResource.data
                val painter = rememberAsyncImagePainter(model = character.image)
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
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(CircleShape),
                        )

                        Spacer(Modifier.height(32.dp))
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = character.name,
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
                                text = stringResource(R.string.character_detail__status),
                                color = CharacterStatus.getColor(character.status),
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Row (
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = character.status,
                                    color = CharacterStatus.getColor(character.status),
                                    fontSize = 16.sp
                                )
                                Icon(
                                    imageVector = Icons.Sharp.Circle,
                                    contentDescription = "Circle",
                                    tint = CharacterStatus.getColor(character.status),
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                        .size(10.dp)
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
                                text = stringResource(R.string.character_detail__gender),
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )

                            Row (
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = character.gender,
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
                                text = stringResource(R.string.character_detail__species),
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )

                            Row (
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = character.species,
                                    fontSize = 16.sp,
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