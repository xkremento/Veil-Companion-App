package com.tfg.veilcompanionapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.ui.theme.VeilIconColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun AuthButton(
    isLogin: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true
) {
    val buttonText = if (isLogin) {
        stringResource(R.string.login_string)
    } else {
        stringResource(R.string.register_string)
    }

    val buttonColors = if (isLogin) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

    if (isLogin) {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp),
            enabled = enabled,
            shape = RoundedCornerShape(8.dp),
            colors = buttonColors
        ) {
            Text(
                text = buttonText,
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelLarge,
                fontFamily = fontFamilyVeil
            )
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp),
            enabled = enabled,
            shape = RoundedCornerShape(8.dp),
            colors = buttonColors
        ) {
            Text(
                text = buttonText, fontFamily = fontFamilyVeil, fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    MaterialTheme {
        AuthButton(
            isLogin = true, onClick = {}, modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterButtonPreview() {
    MaterialTheme {
        AuthButton(
            isLogin = false, onClick = {}, modifier = Modifier.padding(16.dp)
        )
    }
}

// Add and back buttons

@Composable
fun BtnComponent(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(
            imageVector = icon, contentDescription = contentDescription, tint = VeilIconColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BtnBackPreview() {
    MaterialTheme {
        BtnComponent(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_string),
            onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AddFriendIconButtonPreview() {
    MaterialTheme {
        BtnComponent(
            icon = Icons.Default.Person,
            contentDescription = stringResource(R.string.add_friend_string),
            onClick = {})
    }
}
