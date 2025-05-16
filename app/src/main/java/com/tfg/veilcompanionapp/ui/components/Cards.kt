package com.tfg.veilcompanionapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    date: String,
    role: String,
    duration: String,
    winner: String,
    reward: String,
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
            // Date and Role Row
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = date, fontFamily = fontFamilyVeil, fontSize = 14.sp, color = Color.Black
                )

                val roleColor = when (role) {
                    "Asesino" -> Color.Red
                    "Inocente" -> Color.Blue
                    else -> VeilTitleColor
                }

                Text(
                    text = role, fontFamily = fontFamilyVeil, fontSize = 14.sp, color = roleColor
                )
            }

            // Game Details Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Duration
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
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
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                // Winner
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.winner_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    // Gray box representing winner icon
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Gray)
                    )
                }

                // Reward
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.reward_string),
                        fontFamily = fontFamilyVeil,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = reward,
                        fontFamily = fontFamilyVeil,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
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
            date = "01/01/1970",
            role = "Asesino",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun GameHistoryCardInnocentPreview() {
    MaterialTheme {
        GameHistoryCard(
            date = "01/01/1970",
            role = "Inocente",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos",
            modifier = Modifier.padding(16.dp)
        )
    }
}