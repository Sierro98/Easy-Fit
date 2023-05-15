package ies.infantaelena.easy_fit_01

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import ies.infantaelena.easy_fit_01.navigation.AppNavigation
import ies.infantaelena.easy_fit_01.ui.theme.Easy_fit_01Theme

class MainActivity : AppCompatActivity() {
    // Lo que se ejecuta nada mas crear la actividad
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Funcion para forzar portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContent {
            Easy_fit_01Theme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colors.primary,
                    darkIcons = false
                )
                // Llamada al componente que se encarga de gestionar la navegacion
                AppNavigation()
            }
        }
        setupAuth()
    }
    var canAuthenticate = false
    lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    fun setupAuth() {
        if (BiometricManager.from(this)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            canAuthenticate = true
            promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle("Autenticacion Biométrica")
                .setSubtitle("Autgennticacion con sensor biometrico")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL).build()
        }
    }
    fun authenticate (auth: (auth: Boolean)-> Unit){
        if (canAuthenticate){
            androidx.biometric.BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        auth(true)
                    }
                }
            ).authenticate(promptInfo)
        }else{
            auth(false)
        }
    }

}
