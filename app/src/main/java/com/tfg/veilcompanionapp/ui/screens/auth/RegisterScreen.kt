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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Link
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
import com.tfg.veilcompanionapp.ui.components.AuthButton
import com.tfg.veilcompanionapp.ui.components.BtnComponent
import com.tfg.veilcompanionapp.ui.components.EmailTextField
import com.tfg.veilcompanionapp.ui.components.NicknameTextField
import com.tfg.veilcompanionapp.ui.components.PasswordTextField
import com.tfg.veilcompanionapp.ui.components.UrlTextField
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit = {},
    onRegistrationSuccess: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Effect to navigate after a successful registration
    LaunchedEffect(uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            onRegistrationSuccess()
            // Reset state for future registrations
            viewModel.resetState()
        }
    }

    RegisterContent(
        email = uiState.email,
        onEmailChange = { viewModel.updateEmail(it) },
        nickname = uiState.nickname,
        onNicknameChange = { viewModel.updateNickname(it) },
        password = uiState.password,
        onPasswordChange = { viewModel.updatePassword(it) },
        confirmPassword = uiState.confirmPassword,
        onConfirmPasswordChange = { viewModel.updateConfirmPassword(it) },
        profileImageUrl = uiState.profileImageUrl,
        onProfileImageUrlChange = { viewModel.updateProfileImageUrl(it) },
        onRegisterClick = { viewModel.register() },
        onBackClick = onBackClick,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        emailError = uiState.emailError,
        nicknameError = uiState.nicknameError,
        passwordError = uiState.passwordError,
        confirmPasswordError = uiState.confirmPasswordError,
        profileImageUrlError = uiState.profileImageUrlError
    )
}

@Composable
fun RegisterContent(
    email: String,
    onEmailChange: (String) -> Unit,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    profileImageUrl: String,
    onProfileImageUrlChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    emailError: String? = null,
    nicknameError: String? = null,
    passwordError: String? = null,
    confirmPasswordError: String? = null,
    profileImageUrlError: String? = null
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
                .verticalScroll(rememberScrollState())
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
                    text = stringResource(R.string.register_string),
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

            // Register Form
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Email field
                EmailTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    isError = emailError != null,
                    errorMessage = emailError
                )

                // Nickname field
                NicknameTextField(
                    value = nickname,
                    onValueChange = onNicknameChange,
                    isError = nicknameError != null,
                    errorMessage = nicknameError
                )

                // Password field
                PasswordTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    isError = passwordError != null,
                    errorMessage = passwordError
                )

                // Confirm Password field
                PasswordTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = stringResource(R.string.confirm_password_string),
                    isError = confirmPasswordError != null,
                    errorMessage = confirmPasswordError
                )

                // Profile Image URL field (optional)
                UrlTextField(
                    value = profileImageUrl,
                    onValueChange = onProfileImageUrlChange,
                    label = stringResource(R.string.profile_image_url_string),
                    isError = profileImageUrlError != null,
                    errorMessage = profileImageUrlError,
                    imeAction = ImeAction.Done,
                    onImeAction = onRegisterClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Optional fields info
                Text(
                    text = stringResource(R.string.optional_fields_info),
                    fontFamily = fontFamilyVeil,
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Register button
                AuthButton(
                    isLogin = false, onClick = onRegisterClick, enabled = !isLoading
                )

                if (isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        color = VeilTitleColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
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
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterContent(
            email = "",
            onEmailChange = {},
            nickname = "",
            onNicknameChange = {},
            password = "",
            onPasswordChange = {},
            confirmPassword = "",
            onConfirmPasswordChange = {},
            profileImageUrl = "",
            onProfileImageUrlChange = {},
            onRegisterClick = {},
            onBackClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun RegisterScreenWithDataPreview() {
    MaterialTheme {
        RegisterContent(
            email = "user@example.com",
            onEmailChange = {},
            nickname = "gamer123",
            onNicknameChange = {},
            password = "Password123!",
            onPasswordChange = {},
            confirmPassword = "Password123!",
            onConfirmPasswordChange = {},
            profileImageUrl = "https://example.com/image.jpg",
            onProfileImageUrlChange = {},
            onRegisterClick = {},
            onBackClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun RegisterScreenErrorPreview() {
    MaterialTheme {
        RegisterContent(
            email = "invalid-email",
            onEmailChange = {},
            nickname = "a",
            onNicknameChange = {},
            password = "123",
            onPasswordChange = {},
            confirmPassword = "456",
            onConfirmPasswordChange = {},
            profileImageUrl = "not-a-url",
            onProfileImageUrlChange = {},
            onRegisterClick = {},
            onBackClick = {},
            emailError = "Introduce un email válido",
            nicknameError = "El nickname debe tener al menos 3 caracteres",
            passwordError = "La contraseña debe tener al menos 8 caracteres",
            confirmPasswordError = "Las contraseñas no coinciden",
            profileImageUrlError = "Introduce una URL válida para la imagen de perfil"
        )
    }
}