package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.maps.android.compose.CameraPositionState
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.ActivityType
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.other.Constants
import ies.infantaelena.easy_fit_01.other.TrackingUtility
import ies.infantaelena.easy_fit_01.services.Polyline
import ies.infantaelena.easy_fit_01.services.TrackingService
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/**
 * Clase con la funcionalidad de RunActivityScreen
 */
class ActivityViewModel() : ViewModel() {
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

    fun saveActivity(mainActivity: MainActivity, activityType: ActivityType) {
        val aux: Double = currentSteps.toDouble().div(500)
        if (mainActivity.user.actividades.isNullOrEmpty() && calculateTimeInHours(_formattedTime) > 3) {
            mainActivity.user.actividades = mutableStateListOf(
                Activity(
                    activityType = activityType.toString(),
                    date = LocalDate.now().toString(),
                    distance = currentSteps.toString(),
                    experience = aux.toString(),
                    time = _formattedTime,
                    pathPoints = pathPoints.last().toList()
                )
            )
        } else if (calculateTimeInHours(_formattedTime) > 3) {
            mainActivity.user.actividades!!.add(
                Activity(
                    activityType = activityType.toString(),
                    date = LocalDate.now().toString(),
                    distance = currentSteps.toString(),
                    experience = aux.toString(),
                    time = _formattedTime,
                    pathPoints = pathPoints.last().toList()
                )
            )
        }

        val database: DatabaseReference =
            FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference.child("users")
        val useruid: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        //Guardado de datos en la Raltime Database
        var auxUse: Double? = mainActivity.user.exp?.toDouble()?.plus(aux)
        var auxLv: Int = 0
        if (auxUse != null) {
            if (auxUse >= 100.00) {
                auxUse -= 100
                auxLv = 1
            }
        }
        val updatedUser = Usuario(
            actividades = mainActivity.user.actividades,
            email = mainActivity.user.email,
            level = mainActivity.user.level?.toInt()?.plus(auxLv).toString(),
            exp = auxUse.toString(),
            username = mainActivity.user.username,
            challenges = mainActivity.user.challenges
        )
        mainActivity.user = updatedUser
        database.child(useruid?.uid.toString()).setValue(
            updatedUser
        )
    }

    fun calculateTimeInHours(tiempoString: String): Int {
        val tiempo: LocalTime =
            LocalTime.parse(tiempoString, DateTimeFormatter.ofPattern("HH:mm:ss:SS"))
        return tiempo.toSecondOfDay()
    }
}