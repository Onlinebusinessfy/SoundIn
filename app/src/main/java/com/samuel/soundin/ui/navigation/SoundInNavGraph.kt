package com.samuel.soundin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Popup
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.samuel.soundin.ui.screens.LoginScreen
import com.samuel.soundin.ui.screens.MainScreen
import com.samuel.soundin.ui.screens.PlaylistDetailScreen
import com.samuel.soundin.ui.screens.RegisterScreen
import com.samuel.soundin.viewmodels.UserSessionViewModel

@Composable
fun SoundInNavGraph(
    navController: NavHostController,
    sessionViewModel: UserSessionViewModel
){
    // NavHost is a container that will hold the screens
    // NavHost is a NavController and a StratDestination
    NavHost(
        navController = navController,
        startDestination = SoundInRoutes.LOGIN
    ){
        composable(SoundInRoutes.LOGIN){
            LoginScreen(
                sessionViewModel = sessionViewModel,
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
            MainScreen(
                sessionViewModel = sessionViewModel,
                onLogOut = {
                    sessionViewModel.logout()
                    navController.navigate(SoundInRoutes.LOGIN){
                        popUpTo(SoundInRoutes.MAIN){inclusive = true}
                    }
                },
                onNavigationToPlaylistDetail = { playlist ->
                    navController.navigate("playlistDetail/${playlist.id}")
                }
            )
        }
        composable (
            SoundInRoutes.PLAYLIST_DETAIL,
            arguments = listOf(navArgument("playlistId"){type = NavType.IntType})
        ){ backStackEntry ->
            val playlistId = backStackEntry.arguments?.getInt("playlistId") ?: 0
            PlaylistDetailScreen (
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}