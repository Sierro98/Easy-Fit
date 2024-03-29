package ies.infantaelena.easy_fit_01.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.infantaelena.easy_fit_01.LoginScreen
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.MainScreen
import ies.infantaelena.easy_fit_01.SplashScreen
import ies.infantaelena.easy_fit_01.views.CalisthenicsActivityScreen
import ies.infantaelena.easy_fit_01.views.ChallengeScreen
import ies.infantaelena.easy_fit_01.views.CyclingActivityScreen
import ies.infantaelena.easy_fit_01.views.HikingActivityScreen
import ies.infantaelena.easy_fit_01.views.InfoScreen
import ies.infantaelena.easy_fit_01.views.RecoberSreen
import ies.infantaelena.easy_fit_01.views.RegisterScreen
import ies.infantaelena.easy_fit_01.views.RunActivityScreen
import ies.infantaelena.easy_fit_01.views.TeamActivityScreen
import ies.infantaelena.easy_fit_01.views.UserScreen
import ies.infantaelena.easy_fit_01.views.WalkActivityScreen

@Composable
fun AppNavigation(activity: MainActivity) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController, mainActivity = activity)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, activity = activity)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, mainActivity = activity)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.RecoberScreen.route){
            RecoberSreen(navController = navController)
        }
        composable(route = Screen.RunActivityScreen.route) {
            RunActivityScreen(navController = navController, mainActivity = activity)
        }
        composable(route = Screen.WalkActivityScreen.route) {
            WalkActivityScreen( mainActivity = activity)
        }
        composable(route = Screen.HikingActivityScreen.route) {
            HikingActivityScreen( mainActivity = activity)
        }
        composable(route = Screen.CyclingActivityScreen.route) {
            CyclingActivityScreen( mainActivity = activity)
        }
        composable(route = Screen.CalisthenicsActivityScreen.route) {
            CalisthenicsActivityScreen( mainActivity = activity)
        }
        composable(route = Screen.TeamActivityScreen.route) {
            TeamActivityScreen( mainActivity = activity)
        }
        composable(route = Screen.UserSreen.route) {
            UserScreen(navController = navController,mainActivity = activity)
        }
        composable(route = Screen.ChallengeScreen.route) {
            ChallengeScreen(navController = navController, mainActivity = activity)
        }
        composable(route = Screen.InfoScreen.route) {
            InfoScreen()
        }
    }
}