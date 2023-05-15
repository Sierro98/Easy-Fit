package ies.infantaelena.easy_fit_01.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.infantaelena.easy_fit_01.LoginScreen
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.MainScreen
import ies.infantaelena.easy_fit_01.SplashScreen
import ies.infantaelena.easy_fit_01.views.RecoberSreen
import ies.infantaelena.easy_fit_01.views.RegisterScreen
import ies.infantaelena.easy_fit_01.views.RunActivityScreen

@Composable
fun AppNavigation(activity: MainActivity) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, activity = activity)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.RecoberScreen.route){
            RecoberSreen(navController = navController)
        }
        composable(route = Screen.RunActivityScreen.route) {
            RunActivityScreen(navController = navController)
        }
    }
}