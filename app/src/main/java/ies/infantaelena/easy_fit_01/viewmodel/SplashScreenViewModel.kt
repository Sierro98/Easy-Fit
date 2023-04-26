package ies.infantaelena.easy_fit_01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ies.infantaelena.easy_fit_01.navigation.Screen

class SplashScreenViewModel(): ViewModel() {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    fun checkIfLogin(nav:NavController){
        Thread.sleep(1000)
        if (user != null){
            nav.popBackStack()
            nav.navigate(route = Screen.MainScreen.route)
        }else{
            nav.popBackStack()
            nav.navigate(route = Screen.LoginScreen.route)
        }
    }
}