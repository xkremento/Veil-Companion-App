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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.ui.components.BtnComponent
import com.tfg.veilcompanionapp.ui.components.EmailTextField
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun AddFriendScreen(
    onBackClick: () -> Unit = {},
    onFriendRequestSent: () -> Unit = {},
    viewModel: AddFriendViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isRequestSent) {
        if (uiState.isRequestSent) {
            onFriendRequestSent()

            viewModel.resetState()
        }
    }

    AddFriendContent(
        email = uiState.email,
        onEmailChange = { viewModel.updateEmail(it) },
        onSendRequestClick = { viewModel.sendFriendRequest() },
        onBackClick = onBackClick,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        emailError = uiState.emailError
    )
}

@Composable
fun AddFriendContent(
    email: String,
    onEmailChange: (String) -> Unit,
    onSendRequestClick: () -> Unit,
    onBackClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    emailError: String? = null
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
                    text = stringResource(R.string.add_friend_string),
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

            // Content section (takes up remaining space)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                // Info text
                Text(
                    text = stringResource(R.string.add_friend_instruction),
                    fontFamily = fontFamilyVeil,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Email field
                EmailTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    isError = emailError != null,
                    errorMessage = emailError,
                    imeAction = ImeAction.Done,
                    onImeAction = onSendRequestClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Bottom section with button and loading indicator
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = VeilTitleColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Send Request button - now at the bottom
                Button(
                    onClick = onSendRequestClick,
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VeilTitleColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.send_request_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        // Error message
        errorMessage?.let { error ->
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
fun AddFriendScreenPreview() {
    MaterialTheme {
        AddFriendContent(email = "", onEmailChange = {}, onSendRequestClick = {}, onBackClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddFriendScreenWithDataPreview() {
    MaterialTheme {
        AddFriendContent(
            email = "friend@example.com",
            onEmailChange = {},
            onSendRequestClick = {},
            onBackClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddFriendScreenLoadingPreview() {
    MaterialTheme {
        AddFriendContent(
            email = "friend@example.com",
            onEmailChange = {},
            onSendRequestClick = {},
            onBackClick = {},
            isLoading = true
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddFriendScreenErrorPreview() {
    MaterialTheme {
        AddFriendContent(
            email = "invalid-email",
            onEmailChange = {},
            onSendRequestClick = {},
            onBackClick = {},
            emailError = "Introduce un email válido"
        )
    }
}