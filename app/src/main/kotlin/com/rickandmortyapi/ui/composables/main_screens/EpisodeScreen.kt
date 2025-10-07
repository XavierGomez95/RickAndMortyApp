package com.rickandmortyapi.ui.composables.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rickandmortyapi.data.model.Episode

@Composable
fun EpisodeScreen (
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .padding(12.dp)
    ) {
        val fakeList = mutableListOf(
            Episode("Pilot", "https://example.com/episode1.jpg"),
            Episode("Lawnmower Dog", "https://example.com/episode2.jpg"),
            Episode("Anatomy Park", "https://example.com/episode3.jpg"),
            Episode("M. Night Shaym-Aliens!", "https://example.com/episode4.jpg")
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    top = 100.dp,
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            fakeList.forEach { character ->
                Text(
                    text = "Name: ${character.name} \\" +
                            "Image: ${character.image} \\",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}