package com.rickandmortyapi.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.rickandmortyapi.R
import com.rickandmortyapi.ui.theme.LocalSemanticColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val logo = R.drawable.rick_morty_logo
    val colors = LocalSemanticColors.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.topBarBackground
        ),
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(logo),
                    contentDescription = stringResource(R.string.content_description__logo),
                    alignment = Alignment.Center,
                    modifier = Modifier
                )
            }
        }
    )
}