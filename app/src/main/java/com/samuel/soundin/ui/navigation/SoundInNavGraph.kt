package com.samuel.soundin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samuel.soundin.ui.screens.LoginScreen
import com.samuel.soundin.ui.screens.MainScreen
import com.samuel.soundin.ui.screens.RegisterScreen

@Composable
fun SoundInNavGraph(
    navController: NavHostController
){
    // NavHost is a container that will hold the screens
    // NavHost is a NavController and a StratDestination
    NavHost(
        navController = navController,
        startDestination = SoundInRoutes.LOGIN
    ){
        composable(SoundInRoutes.LOGIN){
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(SoundInRoutes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate((SoundInRoutes.MAIN))
                }
            )
        }
        composable(SoundInRoutes.REGISTER){
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(SoundInRoutes.LOGIN){
                        popUpTo(SoundInRoutes.LOGIN) {inclusive = true}
                    }
                }
            )
        }
        composable (SoundInRoutes.MAIN){
            MainScreen()
        }
    }
}