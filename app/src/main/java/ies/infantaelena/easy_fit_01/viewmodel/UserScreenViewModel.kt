package ies.infantaelena.easy_fit_01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ies.infantaelena.easy_fit_01.navigation.Screen

class UserScreenViewModel() : ViewModel() {
    fun LogOut(nav: NavController) {
        try {
            FirebaseAuth.getInstance().signOut()
            nav.popBackStack()
            nav.navigate(route = Screen.LoginScreen.route)
        } catch (ex: java.lang.Exception) {
        }
    }

    fun GoToUserPage(nav: NavController){
        nav.popBackStack()
        nav.navigate(route = Screen.UserSreen.route)
    }
}