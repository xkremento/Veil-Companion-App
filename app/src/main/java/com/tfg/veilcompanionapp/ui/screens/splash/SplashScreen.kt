package com.tfg.veilcompanionapp.ui.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun SplashScreen(
    onNavigateToAuth: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val navigationState by viewModel.navigationState.collectAsState()

    // Effect to handle navigation
    LaunchedEffect(navigationState) {
        when (navigationState) {
            is SplashNavigationState.NavigateToAuth -> onNavigateToAuth()
            is SplashNavigationState.NavigateToHome -> onNavigateToHome()
            else -> { /* Loading state, do nothing */
            }
        }
    }

    SplashContent()
}

@Composable
fun SplashContent() {
    // State to control the animation
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "Alpha Animation"
    )

    // Start the animation after composition
    LaunchedEffect(true) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VeilBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim.value)
        ) {
            //"Veil" title
            Text(
                text = stringResource(R.string.title),
                fontSize = 130.sp,
                fontWeight = FontWeight.Bold,
                color = VeilTitleColor,
                letterSpacing = 2.sp,
                fontFamily = fontFamilyVeil
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 40.dp),
                thickness = 2.dp,
                color = Color.Gray
            )

            // "Tricked" subtitle
            Text(
                text = stringResource(R.string.subtitle),
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = fontFamilyVeil
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Denomination
            Text(
                text = stringResource(R.string.denomination),
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = fontFamilyVeil
            )
        }

        // Credits at the bottom
        Text(
            text = stringResource(R.string.credits),
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .alpha(alphaAnim.value)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashContent()
    }
}