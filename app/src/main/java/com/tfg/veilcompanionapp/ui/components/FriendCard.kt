package com.tfg.veilcompanionapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.tfg.veilcompanionapp.R
import com.tfg.veilcompanionapp.domain.model.Friend
import com.tfg.veilcompanionapp.ui.theme.fontFamilyVeil

@Composable
fun FriendCard(
    friend: Friend, onDeleteClick: (String) -> Unit, modifier: Modifier = Modifier
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
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                if (friend.profileImageUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(friend.profileImageUrl),
                        contentDescription = stringResource(R.string.profile_image_string),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Username
            Text(
                text = "$${friend.username}",
                fontFamily = fontFamilyVeil,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )

            // Delete Button
            IconButton(
                onClick = { onDeleteClick(friend.id) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_friend_string),
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun FriendCardPreview() {
    MaterialTheme {
        FriendCard(
            friend = Friend(
                id = "1", username = "username", profileImageUrl = null
            ), onDeleteClick = {}, modifier = Modifier.padding(16.dp)
        )
    }
}