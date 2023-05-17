package ies.infantaelena.easy_fit_01

import android.R
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ies.infantaelena.easy_fit_01.navigation.AppNavigation
import ies.infantaelena.easy_fit_01.ui.theme.Easy_fit_01Theme

class MainActivity : AppCompatActivity() {

    // Lo que se ejecuta nada mas crear la AppCompatActivityactividad
    // Lo que se ejecuta nada mas crear la actividad

    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Funcion para forzar portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        handleNavigationBugOnXiaomiDevices()
        setContent {
            Easy_fit_01Theme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colors.primary,
                    darkIcons = false
                )
                // Llamada al componente que se encarga de gestionar la navegacion
                AppNavigation(this)
            }
        }
        setupAuth()
    }
    var canAuthenticate = false
    lateinit var promptInfo: BiometricPrompt.PromptInfo
    fun setupAuth() {
        if (BiometricManager.from(this)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            canAuthenticate = true
            promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Autenticacion Biométrica")
                .setSubtitle("Autgennticacion con sensor biometrico")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL).build()
        }
    }
    fun authenticate (auth: (auth: Boolean)-> Unit){
        if (canAuthenticate){
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        auth(true)
                    }
                }
            ).authenticate(promptInfo)
        }else{
            auth(false)
        }
    }

    /**
     * Metodo para solucionar un bug de inicio en los dispositivos de Xiaomi
     */
    private fun handleNavigationBugOnXiaomiDevices() {
        window.decorView.post {
            window.setBackgroundDrawableResource(R.color.background_light)
        }
    }
}
