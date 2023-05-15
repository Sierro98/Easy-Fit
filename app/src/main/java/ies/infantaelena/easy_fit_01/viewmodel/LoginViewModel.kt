package ies.infantaelena.easy_fit_01.viewmodel

import android.app.Activity
import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.navigation.Screen

/**
 * Clase con la funcionalidad de LoginScreen
 */
class LoginViewModel() : ViewModel() {
    //Variables para la vista de Login
    var user by mutableStateOf("")
    var password by mutableStateOf("")

    /**
     * Metodo que recibe el email y la contraseña y comprueba si existe en la base de datos y si es asi
     * pasa a la siguiente vista que es la del MainScreen
     * @param email email que recibe desde la vista
     * @param contra contraseña que recibe desde la vista
     * @param context
     * @param nav
     */
    fun checkLogin(email: String, contra: String, context: Context, nav: NavController) {

        if (email.isBlank() || contra.isBlank()) {
            Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
        } else {
            //Intenta logearse en FirebaseAuth con el email y contraseña
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contra)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
                        nav.popBackStack()
                        nav.navigate(route = Screen.MainScreen.route)
                    } else {
                        Toast.makeText(context, "Login fallido", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}

