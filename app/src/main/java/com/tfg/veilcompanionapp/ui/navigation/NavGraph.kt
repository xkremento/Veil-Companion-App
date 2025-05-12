package com.tfg.veilcompanionapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tfg.veilcompanionapp.ui.screens.auth.AuthScreen
import com.tfg.veilcompanionapp.ui.screens.auth.LoginScreen
import com.tfg.veilcompanionapp.ui.screens.auth.RegisterScreen
import com.tfg.veilcompanionapp.ui.screens.friends.AddFriendScreen
import com.tfg.veilcompanionapp.ui.screens.friends.FriendRequestsScreen
import com.tfg.veilcompanionapp.ui.screens.friends.FriendsScreen
import com.tfg.veilcompanionapp.ui.screens.home.HomeScreen
import com.tfg.veilcompanionapp.ui.screens.home.HomeViewModel
import com.tfg.veilcompanionapp.ui.screens.splash.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel

object Route {
    const val SPLASH = "splash"
    const val AUTH = "auth"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val FRIENDS = "friends"
    const val FRIEND_REQUESTS = "friend_requests"
    const val ADD_FRIEND = "add_friend"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.SPLASH
) {
    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        // Splash Screen - Initial loading screen
        composable(Route.SPLASH) {
            SplashScreen(onNavigateToAuth = {
                navController.navigate(Route.AUTH) {
                    popUpTo(Route.SPLASH) { inclusive = true }
                }
            }, onNavigateToHome = {
                navController.navigate(Route.HOME) {
                    popUpTo(Route.SPLASH) { inclusive = true }
                }
            })
        }

        // Auth Screen - Main authentication screen
        composable(Route.AUTH) {
            AuthScreen(onLoginClick = {
                navController.navigate(Route.LOGIN)
            }, onRegisterClick = {
                navController.navigate(Route.REGISTER)
            })
        }

        // Login Screen - Login screen
        composable(Route.LOGIN) {
            LoginScreen(onBackClick = {
                navController.navigateUp()
            }, onLoginSuccess = {
                // Navigate to Home and clear navigation stack so they can't go back
                navController.navigate(Route.HOME) {
                    popUpTo(Route.AUTH) { inclusive = true }
                }
            })
        }

        // Register Screen - Registration screen
        composable(Route.REGISTER) {
            RegisterScreen(onBackClick = {
                navController.navigateUp()
            }, onRegistrationSuccess = {
                // Navigate to Login after successful registration
                navController.navigate(Route.LOGIN) {
                    popUpTo(Route.AUTH) { inclusive = false }
                }
            })
        }

        // Home Screen - Main application screen
        composable(Route.HOME) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(onFriendsClick = {
                navController.navigate(Route.FRIENDS)
            }, onLogoutClick = {
                homeViewModel.logout()
                navController.navigate(Route.AUTH) {
                    popUpTo(Route.HOME) { inclusive = true }
                }
            })
        }

        // Friends Screen - Friends screen
        composable(Route.FRIENDS) {
            FriendsScreen(onBackClick = {
                navController.navigateUp()
            }, onAddFriendClick = {
                navController.navigate(Route.ADD_FRIEND)
            }, onFriendRequestsClick = {
                navController.navigate(Route.FRIEND_REQUESTS)
            })
        }

        // Friend Requests Screen - Friend requests screen
        composable(Route.FRIEND_REQUESTS) {
            FriendRequestsScreen(
                onBackClick = {
                    navController.navigateUp()
                })
        }

        // Add Friend Screen - Add friend screen
        composable(Route.ADD_FRIEND) {
            AddFriendScreen(onBackClick = {
                navController.navigateUp()
            }, onFriendRequestSent = {
                // Return to the friends screen after sending the request
                navController.navigateUp()
            })
        }
    }
}