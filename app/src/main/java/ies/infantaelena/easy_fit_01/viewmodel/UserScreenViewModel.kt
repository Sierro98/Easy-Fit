package ies.infantaelena.easy_fit_01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ies.infantaelena.easy_fit_01.navigation.Screen

class UserScreenViewModel() : ViewModel() {
    fun LogOut(nav: NavController) {
        try {
            FirebaseAuth.getInstance().signOut()
            nav.navigate(route = Screen.LoginScreen.route) {
                popUpTo(route = Screen.UserSreen.route) {
                    inclusive = true
                }
            }
        } catch (_: java.lang.Exception) {
        }
    }

}