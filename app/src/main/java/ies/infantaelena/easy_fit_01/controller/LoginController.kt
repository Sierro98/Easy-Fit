package ies.infantaelena.easy_fit_01.controller

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ies.infantaelena.easy_fit_01.navigation.Screen

fun checkLogin(email: String, contra: String, context: Context, nav: NavController) {
    // Declaracion de la referencia a la base de datos
    lateinit var database: DatabaseReference

    if (email.isBlank() || contra.isBlank()) {
        Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
    } else {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contra).addOnCompleteListener {
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