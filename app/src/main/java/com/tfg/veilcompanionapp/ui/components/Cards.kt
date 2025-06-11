package com.tfg.veilcompanionapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    imageResource: Int = R.drawable.veil_tricked_logo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "#${id}",
                    fontFamily = fontFamilyVeil,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = duration,
                    fontFamily = fontFamilyVeil,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "logo",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = date,
                    fontFamily = fontFamilyVeil,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

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
                    color = roleColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameHistoryCardPreviewAssassin() {
    GameHistoryCard(
        id = 1,
        date = "12/06/2025",
        role = "Asesino",
        duration = "5:30"
    )
}

@Preview(showBackground = true)
@Composable
fun GameHistoryCardPreviewInnocent() {
    GameHistoryCard(
        id = 2,
        date = "11/06/2025",
        role = "Inocente",
        duration = "8:15"
    )
}