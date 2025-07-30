package com.example.whatsapp_status_saver_app.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(Screens.HomeScreen.route + "/{countryCode}") { backStackEntry ->
            val countryCode = backStackEntry.arguments?.getString("countryCode")
            HomeScreen(navController, countryCode)
        }


        composable(Screens.LanguageSelectionScreen.route) {
            LanguageSelectionScreen(navController)
        }

        composable(Screens.StatusScreen.route) {
            StatusScreen(navController)
        }

        composable(Screens.SingleViewScreen.route) {
            SingleViewScreen(navController)
        }

        composable(Screens.DirectChatScreen.route) {
            DirectChatScreen(navController)
        }

        composable(Screens.PrivacyPolicy.route) {
            PrivacyPolicy(navController)
        }



    }
}


sealed class Screens(
    val route: String,
    val title: String,
    val Icon: ImageVector,

    ) {
    object SplashScreen : Screens(
        "SplashScreen",
        "SplashScreen",
        Icon = Icons.Filled.Add,
    )

    object HomeScreen : Screens(
        "HomeScreen",
        "HomeScreen",
        Icon = Icons.Filled.Add,
    )

    object LanguageSelectionScreen : Screens(
        "LanguageSelectionScreen",
        "LanguageSelectionScreen",
        Icon = Icons.Filled.Add,
    )

    object StatusScreen : Screens(
        "StatusScreen",
        "StatusScreen",
        Icon = Icons.Filled.Add,
    )

    object SingleViewScreen : Screens(
        "SingleViewScreen",
        "SingleViewScreen",
        Icon = Icons.Filled.Add,
    )

    object DirectChatScreen : Screens(
        "DirectChatScreen",
        "DirectChatScreen",
        Icon = Icons.Filled.Add,
    )

    object PrivacyPolicy : Screens(
        "PrivacyPolicy",
        "PrivacyPolicy",
        Icon = Icons.Filled.Add,
    )

}




