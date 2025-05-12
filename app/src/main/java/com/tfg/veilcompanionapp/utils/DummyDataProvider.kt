package com.tfg.veilcompanionapp.utils

import com.tfg.veilcompanionapp.domain.model.Friend
import com.tfg.veilcompanionapp.domain.model.FriendRequest
import com.tfg.veilcompanionapp.domain.model.Game

/**
 * Class to provide sample data during development
 * Replace with real API data when available
 */
object DummyDataProvider {

    // User data
    data class DummyUser(
        val username: String,
        val profileImageUrl: String?,
        val points: Int,
        val friends: Int,
        val coins: Int
    )

    fun getDummyUser() = DummyUser(
        username = "Username",
        profileImageUrl = null,
        points = 100,
        friends = 5,
        coins = 200
    )

    // Game history
    fun getDummyGames() = listOf(
        Game(
            id = 1L,
            date = "01/01/1970",
            role = "Asesino / Inocente",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos"
        ),
        Game(
            id = 2L,
            date = "01/01/1970",
            role = "Asesino / Inocente",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos"
        )
    )

    // Friends list
    fun getDummyFriends() = listOf(
        Friend(
            id = "1",
            username = "username1",
            profileImageUrl = null
        ),
        Friend(
            id = "2",
            username = "username2",
            profileImageUrl = null
        ),
        Friend(
            id = "3",
            username = "username3",
            profileImageUrl = null
        ),
        Friend(
            id = "4",
            username = "username4",
            profileImageUrl = null
        ),
        Friend(
            id = "5",
            username = "longerusername",
            profileImageUrl = null
        )
    )

    // Friend requests
    fun getDummyFriendRequests() = listOf(
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
}