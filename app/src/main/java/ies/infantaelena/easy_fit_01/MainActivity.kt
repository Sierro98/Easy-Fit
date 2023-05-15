package ies.infantaelena.easy_fit_01

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import ies.infantaelena.easy_fit_01.navigation.AppNavigation
import ies.infantaelena.easy_fit_01.ui.theme.Easy_fit_01Theme
import ies.infantaelena.easy_fit_01.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    // Lo que se ejecuta nada mas crear la AppCompatActivityactividad
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
                AppNavigation(this)
            }

        }
    }

}

