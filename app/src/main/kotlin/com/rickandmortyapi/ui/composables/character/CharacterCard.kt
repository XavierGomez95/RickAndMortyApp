package com.rickandmortyapi.ui.composables.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rickandmortyapi.R
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.ui.theme.LocalSemanticColors
import com.rickandmortyapi.utils.applyDefaultCoilConfig


@Composable
fun CharacterCard(
    character: CharacterModel,
    onClick: (CharacterModel) -> Unit
) {
    val colors = LocalSemanticColors.current

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(character.image)
            .applyDefaultCoilConfig()
            .build()
    )

    val painterState = painter.state

    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable { onClick(character) },
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = colors.cardBackground),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (painterState is AsyncImagePainter.State.Error) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(80.dp)
                        ) {
                            Image(
                                painter = painter,
                                contentDescription = stringResource(R.string.image_not_available_description, character.name),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        Text(
                            modifier = Modifier.clearAndSetSemantics { },
                            text = stringResource(R.string.image_not_available),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.cardTittle.copy(alpha = 0.8f)
                        )
                    }
                } else {
                    Image(
                        painter = painter,
                        contentDescription = stringResource(id = R.string.character_image_description, character.name),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                }

                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    modifier = Modifier.clearAndSetSemantics { },
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.cardTittle
                )
            }
        }
    }
}