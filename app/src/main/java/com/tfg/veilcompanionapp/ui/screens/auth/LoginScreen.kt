package com.tfg.veilcompanionapp.ui.screens.auth

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.ui.components.AuthButton
import com.tfg.veilcompanionapp.ui.components.BtnComponent
import com.tfg.veilcompanionapp.ui.components.EmailTextField
import com.tfg.veilcompanionapp.ui.components.PasswordTextField
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun LoginScreen(
    onBackClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Effect to navigate after a successful login
    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
            // Reset state for future logins
            viewModel.resetState()
        }
    }

    LoginContent(
        email = uiState.email,
        onEmailChange = { viewModel.updateEmail(it) },
        password = uiState.password,
        onPasswordChange = { viewModel.updatePassword(it) },
        onLoginClick = { viewModel.login() },
        onBackClick = onBackClick,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        emailError = uiState.emailError,
        passwordError = uiState.passwordError
    )
}

@Composable
fun LoginContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    emailError: String? = null,
    passwordError: String? = null
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
                    text = stringResource(R.string.login_string),
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

            // Form fields in the center
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Email field
                EmailTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    isError = emailError != null,
                    errorMessage = emailError,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Password field
                PasswordTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    isError = passwordError != null,
                    errorMessage = passwordError,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                    onImeAction = onLoginClick,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // Login button and loading indicator at the bottom
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

                AuthButton(
                    isLogin = true,
                    onClick = onLoginClick,
                    enabled = !isLoading
                )
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
fun LoginScreenPreview() {
    MaterialTheme {
        LoginContent(
            email = "",
            onEmailChange = {},
            password = "",
            onPasswordChange = {},
            onLoginClick = {},
            onBackClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun LoginScreenWithDataPreview() {
    MaterialTheme {
        LoginContent(
            email = "user@example.com",
            onEmailChange = {},
            password = "password123",
            onPasswordChange = {},
            onLoginClick = {},
            onBackClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun LoginScreenLoadingPreview() {
    MaterialTheme {
        LoginContent(
            email = "user@example.com",
            onEmailChange = {},
            password = "password123",
            onPasswordChange = {},
            onLoginClick = {},
            onBackClick = {},
            isLoading = true
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun LoginScreenErrorPreview() {
    MaterialTheme {
        LoginContent(
            email = "invalid-email",
            onEmailChange = {},
            password = "123",
            onPasswordChange = {},
            onLoginClick = {},
            onBackClick = {},
            emailError = "Introduce un email válido",
            passwordError = "La contraseña debe tener al menos 8 caracteres"
        )
    }
}