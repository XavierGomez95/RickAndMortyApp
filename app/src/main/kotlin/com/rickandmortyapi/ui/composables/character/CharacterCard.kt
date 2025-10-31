package com.rickandmortyapi.ui.composables.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.ui.theme.LocalSemanticColors


@Composable
fun CharacterCard (
    character: CharacterModel,
    onClick: (CharacterModel) -> Unit
) {
    val colors = LocalSemanticColors.current

    Box(
        Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp)
        .clickable { onClick(character) },
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3ECE4)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Column {
                val painter = rememberAsyncImagePainter(model = character.image)
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

                    Spacer(
                        modifier = Modifier.padding(start = 40.dp)
                    )

                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = colors.text
                    )
                }
            }
        }
    }
}