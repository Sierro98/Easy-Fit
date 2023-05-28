package ies.infantaelena.easy_fit_01.viewmodel

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.maps.android.SphericalUtil
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

/**
 * Clase con la funcionalidad de MainScreen
 */
class MainScreenViewModel() : ViewModel() {
    private val _isloading = MutableStateFlow(false)
    val isloading = _isloading.asStateFlow()

    var tipoActividad: String by mutableStateOf("");
    private var totalDistance: Double = 0.0;

    /**
     * Funcion que se encarga de deslogear el usuario de Firebase Authentication
     */
    fun LogOut(nav: NavController) {
        try {
            FirebaseAuth.getInstance().signOut()
            nav.navigate(route = Screen.LoginScreen.route) {
                popUpTo(route = Screen.MainScreen.route) {
                    inclusive = true
                }
            }
        } catch (_: java.lang.Exception) {
        }
    }

    fun GoToUserPage(nav: NavController) {
        nav.navigate(route = Screen.UserSreen.route)
    }

    private fun calculateDistance(pointA: LatLng, pointB: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(pointA, pointB);
    }

    fun calculateTotalDistance(activity: Activity): Double {
        totalDistance = 0.0
        var i = 0
        while (i < activity.pathPoints.size - 1) {
            totalDistance += calculateDistance(
                activity.pathPoints[i],
                activity.pathPoints[i + 1]
            )
            i++
        }
        return (totalDistance / 10).roundToInt() / 100.0
    }

    fun calculateTimeInHours(activity: Activity): Double {
        val tiempo: LocalTime =
            LocalTime.parse(activity.time, DateTimeFormatter.ofPattern("HH:mm:ss:SS"))
        return tiempo.toSecondOfDay() / 3600.00
    }

    fun calculateAvgSpeed(distance: Double, time: Double): Double {
        val avgSpeed = distance / time
        return (avgSpeed * 10.0).roundToInt() / 10.0
    }

    init {
        loadActivities()
    }

    fun loadActivities() {
        viewModelScope.launch {
            _isloading.value = true
            delay(1000L) // TODO: cargar aqui los datos/actividades del usuario
            _isloading.value = false
        }
    }

}