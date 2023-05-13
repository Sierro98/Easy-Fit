package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.ViewModel
import ies.infantaelena.easy_fit_01.services.TrackingService

/**
 * Clase con la funcionalidad de RunActivityScreen
 */
class RunActivityScreenViewModel: ViewModel() {
    var contadorPlay: Int by mutableStateOf(0)

    /**
     * Funcion que inicia o para el servicio de tracking, la constante String que reciba
     */
    fun startStopActivity(context: Context, action: String){
        Intent(context, TrackingService::class.java).also {
            it.action = action
            context.startService(it)
        }
    }
}