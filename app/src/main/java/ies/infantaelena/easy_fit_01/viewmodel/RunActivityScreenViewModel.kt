package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.CameraPositionState
import ies.infantaelena.easy_fit_01.other.Constants
import ies.infantaelena.easy_fit_01.other.TrackingUtility
import ies.infantaelena.easy_fit_01.services.Polyline
import ies.infantaelena.easy_fit_01.services.TrackingService
import kotlinx.coroutines.launch


/**
 * Clase con la funcionalidad de RunActivityScreen
 */
class RunActivityScreenViewModel() : ViewModel() {
    var _formattedTime: String by mutableStateOf("00:00:00")

    var _isTracking: Boolean by mutableStateOf(false)

    private val _pathPoints = mutableStateListOf<Polyline>()
    var pathPoints: List<Polyline> = _pathPoints

    private var curTimeInMillis = 0L

    var currentSteps: Int by mutableStateOf(0)


    /**
     * Funcion que inicia o para el servicio de tracking, la constante String que reciba
     */
    fun startStopActivity(context: Context, action: String) {
        Intent(context, TrackingService::class.java).also {
            it.action = action
            context.startService(it)
        }
    }

    private fun addPathPoint(point: Polyline) {
        _pathPoints.add(point)
    }


    fun updatePosition(cameraPositionState: CameraPositionState, pathPoints: List<Polyline>) {
        viewModelScope.launch {
            cameraPositionState.move(
                update = CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().toList().last(),
                    Constants.MAP_ZOOM
                ),
            )
        }
    }


    fun subscribe2Observers(lifecycle: LifecycleOwner) {
        TrackingService.pathPoints.observe(lifecycle) {
            if (!it.isNullOrEmpty()) {
                addPathPoint(it.last())
            }
        }

        TrackingService.isTracking.observe(lifecycle) {
            _isTracking = it
        }

        TrackingService.timeRunInMillis.observe(lifecycle) {
            curTimeInMillis = it
            _formattedTime = TrackingUtility.getFormattedStopWatchTimer(curTimeInMillis, true)
        }

        TrackingService.curSteps.observe(lifecycle) {
            currentSteps = it
        }
    }

    fun uploadActivity() {
        // TODO: Subir los siguientes datos
//        _formattedTime
//        pathPoints
//        currentSteps
    }
}