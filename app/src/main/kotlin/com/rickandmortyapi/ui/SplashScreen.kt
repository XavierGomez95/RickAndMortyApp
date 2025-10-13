package com.rickandmortyapi.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rickandmortyapi.R
import com.rickandmortyapi.ui.navigation.MainNavigation
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
object Splash


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SplashScreen(navController: NavController) {
    val logo = R.drawable.rick_morty_logo

    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()
    ) {
        val targetDp = maxHeight / 5
        var startAnimation by remember { mutableStateOf(false) }

        val logoAxisY by animateDpAsState(
            targetValue = if (startAnimation) -targetDp else 0.dp, // Move logo above
            animationSpec = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            ),
            label = "AxisY"
        )

        LaunchedEffect(Unit) {
            delay(1000)
            startAnimation = true
            delay(1500)

            navController.navigate(MainNavigation) {
                popUpTo(Splash) { inclusive = true }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = logoAxisY)
            )
        }
    }
}