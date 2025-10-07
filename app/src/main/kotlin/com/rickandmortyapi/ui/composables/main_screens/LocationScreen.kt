package com.rickandmortyapi.ui.composables.main_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rickandmortyapi.data.model.Location

@Composable
fun LocationScreen (
    modifier: Modifier
) {
    val fakeList = mutableListOf(
        Location("Citadel of Ricks", "https://example.com/location1.jpg"),
        Location("Earth (C-137)", "https://example.com/location2.jpg"),
        Location("Anatomy Park", "https://example.com/location3.jpg"),
        Location("Bird World", "https://example.com/location4.jpg")
    )

    Column(
        modifier = modifier
            .padding(
                top = 100.dp,
                start = 20.dp,
                end = 20.dp
            )
    ) {
        fakeList.forEach { location ->
            Text(
                text = "Name: ${location.name}\n" +
                        "Image URL: ${location.image}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}