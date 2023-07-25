package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import ies.infantaelena.easy_fit_01.R

class RecoverViewModel : ViewModel() {
    var email by mutableStateOf("")

    fun recupPass(email: String, context: Context, nav: Any) {
        if (email.isBlank()) {
            Toast.makeText(context, context.getString(R.string.toast_recover_email), Toast.LENGTH_SHORT).show()
            //TODO: Hay que hcaer los string de los toast
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, context.getString(R.string.toast_error_recover), Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, context.getString(R.string.toast_succes_recover), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, context.getString(R.string.toast_login_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}