package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RecoverViewModel : ViewModel() {
    var email by mutableStateOf("")

    fun recupPass(email: String, context: Context, nav: Any) {
        if (email.isBlank()) {
            Toast.makeText(context, "Rellene el email", Toast.LENGTH_SHORT).show()
            //TODO: Hay que hcaer los string de los toast
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Fomato email incorrecto", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Correo enviado con exito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Login fallido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}