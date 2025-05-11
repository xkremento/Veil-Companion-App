package com.tfg.veilcompanionapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.domain.model.Game
import com.tfg.veilcompanionapp.ui.components.GameHistoryCard
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun HomeScreen(
    onFriendsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        onFriendsClick = onFriendsClick,
        onLogoutClick = onLogoutClick,
        onRefresh = { viewModel.refreshData() }
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onFriendsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VeilBackgroundColor)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = VeilTitleColor
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Profile Section
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Logout Icon Button (NUEVO)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(R.string.logout_string),
                            tint = VeilTitleColor,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { onLogoutClick() }
                        )

                        // Username
                        Text(
                            text = uiState.username,
                            fontFamily = fontFamilyVeil,
                            fontSize = 24.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )

                        // Friends Icon Button
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.friends_string),
                            tint = VeilTitleColor,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { onFriendsClick() }
                        )
                    }
                }

                // Profile Details Row
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Profile Image
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.White)
                                .clip(RoundedCornerShape(4.dp))
                        ) {
                            if (uiState.profileImageUrl != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(uiState.profileImageUrl),
                                    contentDescription = stringResource(R.string.profile_image_string),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        // Stats
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.points.toString(),
                                fontFamily = fontFamilyVeil,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = stringResource(R.string.points_string),
                                fontFamily = fontFamilyVeil,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.friends.toString(),
                                fontFamily = fontFamilyVeil,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = stringResource(R.string.friends_string),
                                fontFamily = fontFamilyVeil,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.coins.toString(),
                                fontFamily = fontFamilyVeil,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = stringResource(R.string.coins_string),
                                fontFamily = fontFamilyVeil,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                // Recent Games Section
                item {
                    Text(
                        text = stringResource(R.string.recent_games_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Game History Cards
                items(uiState.games) { game ->
                    GameHistoryCard(
                        date = game.date,
                        role = game.role,
                        duration = game.duration,
                        winner = game.winner,
                        reward = game.reward,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // If no game history, show placeholder
                if (uiState.games.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.no_games_string),
                            fontFamily = fontFamilyVeil,
                            fontSize = 16.sp,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp)
                        )
                    }
                }
            }

            // Error handling
            uiState.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(text = error)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    val sampleGames = listOf(
        Game(
            id = 1L,
            date = "01/01/1970",
            role = "Asesino / Inocente",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos"
        ),
        Game(
            id = 2L,
            date = "01/01/1970",
            role = "Asesino / Inocente",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos"
        )
    )

    MaterialTheme {
        HomeContent(
            uiState = HomeUiState(
                username = "Username",
                points = 100,
                friends = 5,
                coins = 200,
                games = sampleGames
            ),
            onFriendsClick = {},
            onRefresh = {},
            onLogoutClick = TODO()
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenEmptyGamesPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeUiState(
                username = "Username",
                points = 100,
                friends = 5,
                coins = 200,
                games = emptyList()
            ),
            onFriendsClick = {},
            onRefresh = {},
            onLogoutClick = TODO()
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenLoadingPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeUiState(
                isLoading = true
            ),
            onFriendsClick = {},
            onRefresh = {},
            onLogoutClick = TODO()
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenErrorPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeUiState(
                username = "Username",
                points = 100,
                friends = 5,
                coins = 200,
                games = emptyList(),
                error = "Error al cargar los datos"
            ),
            onFriendsClick = {},
            onRefresh = {},
            onLogoutClick = TODO()
        )
    }
}