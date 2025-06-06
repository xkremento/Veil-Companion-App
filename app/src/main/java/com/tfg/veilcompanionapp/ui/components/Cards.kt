package com.tfg.veilcompanionapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.ui.theme.VeilTitleColor
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun GameHistoryCard(
    id: Long,
    date: String,
    role: String,
    duration: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // ID and Date Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Game ID
                Text(
                    text = "#${id}",
                    fontFamily = fontFamilyVeil,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                // Date
                Text(
                    text = date,
                    fontFamily = fontFamilyVeil,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            // Game Info Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Duration
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.duration_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = duration,
                        fontFamily = fontFamilyVeil,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }

                // Role
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.role_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Set role color based on role type
                    val roleColor = when (role) {
                        "Asesino" -> Color.Red
                        "Inocente" -> Color.Blue
                        else -> VeilTitleColor // Default color
                    }

                    Text(
                        text = role,
                        fontFamily = fontFamilyVeil,
                        fontSize = 16.sp,
                        color = roleColor,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun GameHistoryCardPreview() {
    MaterialTheme {
        GameHistoryCard(
            id = 12345,
            date = "01/01/1970",
            role = "Asesino",
            duration = "01:01",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun GameHistoryCardInnocentPreview() {
    MaterialTheme {
        GameHistoryCard(
            id = 54321,
            date = "01/01/1970",
            role = "Inocente",
            duration = "01:01",
            modifier = Modifier.padding(16.dp)
        )
    }
}