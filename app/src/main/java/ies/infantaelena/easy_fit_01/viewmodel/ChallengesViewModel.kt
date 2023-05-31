package ies.infantaelena.easy_fit_01.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ies.infantaelena.easy_fit_01.navigation.Screen


class ChallengesViewModel : ViewModel() {
    var tipoActividad: String by mutableStateOf("");

    fun completeChallenge(context: Context, index: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Reto")
        builder.setMessage("¿Desea completar el reto?")
        builder.setPositiveButton(
            "Completar"
        ) { _, _ -> updateChallenge(index) }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    fun updateChallenge(index: Int) {
        //TODO: aqui va el cambio del booleano del reto a true y la suma de experiencia al usuario
    }

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