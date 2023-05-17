package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.CameraPositionState
import ies.infantaelena.easy_fit_01.other.Constants

import ies.infantaelena.easy_fit_01.services.Polyline
import ies.infantaelena.easy_fit_01.services.TrackingService
import kotlinx.coroutines.launch


/**
 * Clase con la funcionalidad de RunActivityScreen
 */
class RunActivityScreenViewModel() : ViewModel() {
    var contadorPlay: Int by mutableStateOf(0)
    var isTracking: Boolean by mutableStateOf(false)
    private val _pathPoints = mutableStateListOf<Polyline>()
    var pathPoints: List<Polyline> = _pathPoints
    private var curTimeInMillis = 0L

    /**
     * Funcion que inicia o para el servicio de tracking, la constante String que reciba
     */
    fun startStopActivity(context: Context, action: String) {
        Intent(context, TrackingService::class.java).also {
            it.action = action
            context.startService(it)
        }
    }

    fun addPathPoint(point: Polyline) {
        _pathPoints.add(point)
    }


    fun updatePosition(cameraPositionState: CameraPositionState, pathPoints: List<Polyline>) {
        viewModelScope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    Constants.MAP_ZOOM
                ),
                durationMs = 200
            )
        }
    }
}