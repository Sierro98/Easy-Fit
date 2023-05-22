package ies.infantaelena.easy_fit_01.viewmodel

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen

/**
 * Clase con la funcionalidad de MainScreen
 */
class MainScreenViewModel() : ViewModel() {

    var tipoActividad: String by mutableStateOf("");

    /**
     * Funcion que se encarga de deslogear el usuario de Firebase Authentication
     */
    fun LogOut(nav: NavController) {
        try {
            FirebaseAuth.getInstance().signOut()
            nav.navigate(route = Screen.LoginScreen.route) {
                popUpTo(route = Screen.MainScreen.route) {
                    inclusive = true
                }
            }
        } catch (_: java.lang.Exception) {
        }
    }

    fun GoToUserPage(nav: NavController){
        nav.navigate(route = Screen.UserSreen.route)
    }

}