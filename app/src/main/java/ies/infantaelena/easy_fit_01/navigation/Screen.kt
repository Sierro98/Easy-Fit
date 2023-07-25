package ies.infantaelena.easy_fit_01.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object MainScreen : Screen("main_screen")
    object RegisterScreen: Screen("register_screen")
    object  RunActivityScreen: Screen("run_screen")
    object WalkActivityScreen: Screen("walk_screen")
    object HikingActivityScreen: Screen("hiking_screen")
    object CyclingActivityScreen: Screen("cycling_screen")
    object CalisthenicsActivityScreen: Screen("calisthenics_screen")
    object TeamActivityScreen: Screen("team_screen")
    object  RecoberScreen: Screen("recober_screen")
    object  UserSreen: Screen("user_screen")
    object ChallengeScreen: Screen("challenge_screen")
    object InfoScreen:Screen("info_screen")
}
