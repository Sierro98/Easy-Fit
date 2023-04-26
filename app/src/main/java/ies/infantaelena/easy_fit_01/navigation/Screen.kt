package ies.infantaelena.easy_fit_01.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object MainScreen : Screen("main_screen")
    object RegisterScreen: Screen("register_screen")
    object  RunActivityScreen: Screen("run_screen")
}
