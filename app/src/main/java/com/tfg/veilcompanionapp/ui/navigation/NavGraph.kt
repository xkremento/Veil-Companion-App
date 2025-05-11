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
import com.tfg.veilcompanionapp.ui.screens.splash.SplashScreen

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
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen - Pantalla inicial de carga
        composable(Route.SPLASH) {
            SplashScreen(
                onNavigateToAuth = {
                    navController.navigate(Route.AUTH) {
                        popUpTo(Route.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Route.HOME) {
                        popUpTo(Route.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // Auth Screen - Pantalla principal de autenticación
        composable(Route.AUTH) {
            AuthScreen(
                onLoginClick = {
                    navController.navigate(Route.LOGIN)
                },
                onRegisterClick = {
                    navController.navigate(Route.REGISTER)
                }
            )
        }

        // Login Screen - Pantalla de inicio de sesión
        composable(Route.LOGIN) {
            LoginScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onLoginSuccess = {
                    // Navegar a Home y limpiar pila de navegación para que no puedan volver atrás
                    navController.navigate(Route.HOME) {
                        popUpTo(Route.AUTH) { inclusive = true }
                    }
                }
            )
        }

        // Register Screen - Pantalla de registro
        composable(Route.REGISTER) {
            RegisterScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onRegistrationSuccess = {
                    // Navegar a Login después del registro exitoso
                    navController.navigate(Route.LOGIN) {
                        popUpTo(Route.AUTH) { inclusive = false }
                    }
                }
            )
        }

        // Home Screen - Pantalla principal de la aplicación
        composable(Route.HOME) {
            HomeScreen(
                onFriendsClick = {
                    navController.navigate(Route.FRIENDS)
                }
            )
        }

        // Friends Screen - Pantalla de amigos
        composable(Route.FRIENDS) {
            FriendsScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onAddFriendClick = {
                    navController.navigate(Route.ADD_FRIEND)
                },
                onFriendRequestsClick = {
                    navController.navigate(Route.FRIEND_REQUESTS)
                }
            )
        }

        // Friend Requests Screen - Pantalla de solicitudes de amistad
        composable(Route.FRIEND_REQUESTS) {
            FriendRequestsScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        // Add Friend Screen - Pantalla para añadir amigos
        composable(Route.ADD_FRIEND) {
            AddFriendScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onFriendRequestSent = {
                    // Volver a la pantalla de amigos después de enviar la solicitud
                    navController.navigateUp()
                }
            )
        }
    }
}