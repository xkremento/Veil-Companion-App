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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.tfg.veilcompanionapp.domain.model.FriendRequest
import com.tfg.veilcompanionapp.ui.components.BtnComponent
import com.tfg.veilcompanionapp.ui.components.FriendRequestCard
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun FriendRequestsScreen(
    onBackClick: () -> Unit = {},
    viewModel: FriendRequestsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FriendRequestsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onAcceptRequest = { requestId -> viewModel.acceptFriendRequest(requestId) },
        onRejectRequest = { requestId -> viewModel.rejectFriendRequest(requestId) },
        onRefresh = { viewModel.refreshFriendRequests() }
    )
}

@Composable
fun FriendRequestsContent(
    uiState: FriendRequestsUiState,
    onBackClick: () -> Unit,
    onAcceptRequest: (Long) -> Unit,
    onRejectRequest: (Long) -> Unit,
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
                    text = stringResource(R.string.friend_requests_string),
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
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = VeilTitleColor
                    )
                }
            } else {
                // Friend Requests List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.friendRequests) { request ->
                        FriendRequestCard(
                            friendRequest = request,
                            onAcceptClick = onAcceptRequest,
                            onRejectClick = onRejectRequest
                        )
                    }

                    // If empty, show message
                    if (uiState.friendRequests.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.no_friend_requests_string),
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
fun FriendRequestsScreenPreview() {
    val sampleRequests = listOf(
        FriendRequest(
            id = 1,
            requesterId = "user1",
            requesterUsername = "username1",
            requesterProfileImageUrl = null
        ),
        FriendRequest(
            id = 2,
            requesterId = "user2",
            requesterUsername = "username2",
            requesterProfileImageUrl = null
        ),
        FriendRequest(
            id = 3,
            requesterId = "user3",
            requesterUsername = "username3",
            requesterProfileImageUrl = null
        )
    )

    MaterialTheme {
        FriendRequestsContent(
            uiState = FriendRequestsUiState(
                friendRequests = sampleRequests
            ),
            onBackClick = {},
            onAcceptRequest = {},
            onRejectRequest = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun FriendRequestsScreenEmptyPreview() {
    MaterialTheme {
        FriendRequestsContent(
            uiState = FriendRequestsUiState(
                friendRequests = emptyList()
            ),
            onBackClick = {},
            onAcceptRequest = {},
            onRejectRequest = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun FriendRequestsScreenLoadingPreview() {
    MaterialTheme {
        FriendRequestsContent(
            uiState = FriendRequestsUiState(
                isLoading = true
            ),
            onBackClick = {},
            onAcceptRequest = {},
            onRejectRequest = {},
            onRefresh = {}
        )
    }
}