package ies.infantaelena.easy_fit_01

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import ies.infantaelena.easy_fit_01.navigation.AppNavigation
import ies.infantaelena.easy_fit_01.ui.theme.Easy_fit_01Theme

class MainActivity : ComponentActivity() {
    // Lo que se ejecuta nada mas crear la actividad
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Funcion para forzar portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContent {
            Easy_fit_01Theme {
                // Llamada al componente que se encarga de gestionar la navegacion
                AppNavigation()
            }
        }
    }
}

