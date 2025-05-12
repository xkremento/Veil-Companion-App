package com.tfg.veilcompanionapp.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.domain.model.Friend
import com.tfg.veilcompanionapp.ui.components.BtnComponent
import com.tfg.veilcompanionapp.ui.components.FriendCard
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun FriendsScreen(
    onBackClick: () -> Unit = {},
    onAddFriendClick: () -> Unit = {},
    onFriendRequestsClick: () -> Unit = {},
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FriendsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddFriendClick = onAddFriendClick,
        onFriendRequestsClick = onFriendRequestsClick,
        onDeleteFriend = { friendId -> viewModel.deleteFriend(friendId) },
        onRefresh = { viewModel.refreshFriends() })
}

@Composable
fun FriendsContent(
    uiState: FriendsUiState,
    onBackClick: () -> Unit,
    onAddFriendClick: () -> Unit,
    onFriendRequestsClick: () -> Unit,
    onDeleteFriend: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VeilBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                BtnComponent(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_string),
                    onClick = onBackClick
                )

                // Title
                Text(
                    text = stringResource(R.string.friends_screen_string),
                    fontFamily = fontFamilyVeil,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                // Empty space to balance the back button
                Spacer(modifier = Modifier.weight(0.2f))
            }

            if (uiState.isLoading) {
                // Loading Indicator
                Box(
                    modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = VeilTitleColor
                    )
                }
            } else {
                // Friends List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.friends) { friend ->
                        FriendCard(
                            friend = friend, onDeleteClick = onDeleteFriend
                        )
                    }

                    // If empty, show message
                    if (uiState.friends.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.no_friends_string),
                                    fontFamily = fontFamilyVeil,
                                    fontSize = 16.sp,
                                    color = Color.LightGray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Add Friend Button
                Button(
                    onClick = onAddFriendClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, contentColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.add_friend_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))

                // Friend Requests Button
                Button(
                    onClick = onFriendRequestsClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, contentColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.requests_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 16.sp
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

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun FriendsScreenPreview() {
    val sampleFriends = listOf(
        Friend(
            id = "1", username = "username1", profileImageUrl = null
        ), Friend(
            id = "2", username = "username2", profileImageUrl = null
        ), Friend(
            id = "3", username = "username3", profileImageUrl = null
        ), Friend(
            id = "4", username = "username4", profileImageUrl = null
        )
    )

    MaterialTheme {
        FriendsContent(
            uiState = FriendsUiState(
            friends = sampleFriends
        ),
            onBackClick = {},
            onAddFriendClick = {},
            onFriendRequestsClick = {},
            onDeleteFriend = {},
            onRefresh = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun FriendsScreenEmptyPreview() {
    MaterialTheme {
        FriendsContent(
            uiState = FriendsUiState(
            friends = emptyList()
        ),
            onBackClick = {},
            onAddFriendClick = {},
            onFriendRequestsClick = {},
            onDeleteFriend = {},
            onRefresh = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun FriendsScreenLoadingPreview() {
    MaterialTheme {
        FriendsContent(
            uiState = FriendsUiState(
            isLoading = true
        ),
            onBackClick = {},
            onAddFriendClick = {},
            onFriendRequestsClick = {},
            onDeleteFriend = {},
            onRefresh = {})
    }
}