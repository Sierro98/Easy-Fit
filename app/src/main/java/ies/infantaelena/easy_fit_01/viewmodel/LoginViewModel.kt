package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ies.infantaelena.easy_fit_01.navigation.Screen

class LoginViewModel(): ViewModel() {
    var user by mutableStateOf("")
    var password by mutableStateOf("")
    fun checkLogin(email: String, contra: String, context: Context, nav: NavController) {
        // Declaracion de la referencia a la base de datos
        lateinit var database: DatabaseReference

        if (email.isBlank() || contra.isBlank()) {
            Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
        } else {
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

