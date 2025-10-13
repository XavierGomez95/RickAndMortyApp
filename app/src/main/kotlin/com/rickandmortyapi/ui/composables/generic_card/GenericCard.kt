package com.rickandmortyapi.ui.composables.generic_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.model.LocationModel

@Composable
fun <T> GenericCard(
    genericModel: T,
    onClick: (T) -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable { onClick(genericModel) },
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(217, 227, 194)
            ),
            modifier = Modifier
                .width(220.dp)
                .clickable { onClick(genericModel) }
        ) {
            Column {
                when (genericModel) {
                    is CharacterModel -> {
                        val painter = rememberAsyncImagePainter(model = genericModel.image)
                        Row(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painter,
                                contentDescription = null, // TODO: stringResources()
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1f)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(
                                    end = 8.dp,
                                    start = 16.dp,
                                    bottom = 16.dp
                                ),
                        ) {
                            Text(
                                text = genericModel.name,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        }
                    }

                    is LocationModel -> {
                        Text(text = "Name: ${genericModel.name}")
                    }

                    is EpisodeModel -> {
                        Text(text = "Name: ${genericModel.name}")
                    }
                }
            }
        }
    }
}