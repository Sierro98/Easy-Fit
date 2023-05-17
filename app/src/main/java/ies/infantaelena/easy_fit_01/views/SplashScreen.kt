package ies.infantaelena.easy_fit_01


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.viewmodel.SplashScreenViewModel


@Composable
fun SplashScreen(navController: NavController, splashScreenViewModel: SplashScreenViewModel = viewModel(),mainActivity: MainActivity) {
    val cont = LocalContext.current
    LaunchedEffect(key1 = true) {
        splashScreenViewModel.checkIfLogin(navController,mainActivity, cont)
    }
    Splash()
}
@Composable
fun Splash() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.easy_fit_logo),
            contentDescription = R.string.logoDescription.toString()
        )
    }
}