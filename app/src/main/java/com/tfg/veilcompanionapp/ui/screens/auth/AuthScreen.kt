package com.tfg.veilcompanionapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.ui.components.AuthButton
import com.tfg.veilcompanionapp.ui.theme.VeilBackgroundColor
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun AuthScreen(
    onLoginClick: () -> Unit = {}, onRegisterClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VeilBackgroundColor)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.title),
                fontSize = 130.sp,
                fontWeight = FontWeight.Bold,
                color = VeilTitleColor,
                letterSpacing = 2.sp,
                fontFamily = fontFamilyVeil
            )
            HorizontalDivider(
                modifier = Modifier.width(280.dp), thickness = 2.dp, color = Color.Gray
            )
            Text(
                text = stringResource(R.string.subtitle),
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = fontFamilyVeil
            )
            Text(
                text = stringResource(R.string.denomination),
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = fontFamilyVeil
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AuthButton(
                isLogin = true, onClick = onLoginClick
            )

            Spacer(modifier = Modifier.height(40.dp))

            AuthButton(
                isLogin = false, onClick = onRegisterClick
            )
        }

        Text(
            text = stringResource(R.string.credits),
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    MaterialTheme {
        AuthScreen()
    }
}