package ies.infantaelena.easy_fit_01.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
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
    fun checkLogin(
        email: String,
        contra: String,
        context: Context,
        nav: NavController,
        activity: MainActivity
    ) {

        if (email.isBlank() || contra.isBlank()) {
            Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
        } else {
            //Intenta logearse en FirebaseAuth con el email y contraseña
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contra)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        authenticate(context = context, activity = activity, nav = nav)
                    } else {
                        Toast.makeText(context, "Login fallido", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    var canAuthenticate = false
    lateinit var promptInfo: BiometricPrompt.PromptInfo
    fun setupAuth(context: Context) {

        if (BiometricManager.from(context)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {

            canAuthenticate = true
            promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Autenticacion Biométrica")
                .setSubtitle("Necesaria para el login")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }

    }

    fun authenticate(context: Context, activity: MainActivity, nav: NavController) {


        if (canAuthenticate) {
            BiometricPrompt(activity, ContextCompat.getMainExecutor(context),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
                        nav.popBackStack()
                        nav.navigate(route = Screen.MainScreen.route)
                    }
                }
            ).authenticate(promptInfo)
        }else{
            Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
            nav.popBackStack()
            nav.navigate(route = Screen.MainScreen.route)
        }
    }
}


