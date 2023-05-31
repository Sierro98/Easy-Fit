package ies.infantaelena.easy_fit_01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.maps.android.SphericalUtil
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.navigation.Screen
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class UserScreenViewModel() : ViewModel() {
    var totalDistance: Double = 0.0
    var pasosTotales: Int = 0
    var tiempo: Double = 0.0
    fun LogOut(nav: NavController) {
        try {
            FirebaseAuth.getInstance().signOut()
            nav.navigate(route = Screen.LoginScreen.route) {
                popUpTo(route = Screen.UserSreen.route) {
                    inclusive = true
                }
            }
        } catch (_: java.lang.Exception) {
        }
    }

    fun GoToUserPage(navController: NavController) {

    }

    fun getPasosTotales(mainActivity: MainActivity): String {
        if (!mainActivity.user.actividades.isNullOrEmpty()) {
            for (i in mainActivity.user.actividades!!) {
                pasosTotales += i.distance?.toInt() ?: 0
            }
            return pasosTotales.toString()
        } else {
            return "0"
        }

    }

    fun getPasosMedios(mainActivity: MainActivity): String {
        if (!mainActivity.user.actividades.isNullOrEmpty()) {
            return pasosTotales.div(mainActivity.user.actividades!!.size).toString()

        } else {
            return "0"
        }
    }

    fun getKmTotales(mainActivity: MainActivity): String {
        if (!mainActivity.user.actividades.isNullOrEmpty()) {
            for (i in mainActivity.user.actividades!!) {
                totalDistance += calculateTotalDistance(i)
            }
            return totalDistance.toString()
        } else {
            return "0"
        }

    }

    fun getKmMedios(mainActivity: MainActivity): String {
        if (!mainActivity.user.actividades.isNullOrEmpty()) {
            return totalDistance.div(mainActivity.user.actividades!!.size).toString()

        } else {
            return "0"
        }
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

    private fun calculateDistance(pointA: LatLng, pointB: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(pointA, pointB);
    }

    fun getLevel(mainActivity: MainActivity): Float {
        if (!mainActivity.user.actividades.isNullOrEmpty()) {
            return mainActivity.user.level?.toFloat()!!
        } else {
            return 0.0F
        }

    }

    fun getTiempoTotal(mainActivity: MainActivity): String {
        if (!mainActivity.user.actividades.isNullOrEmpty()) {
            for (i in mainActivity.user.actividades!!) {
                tiempo += LocalTime.parse(i.time, DateTimeFormatter.ofPattern("HH:mm:ss:SS"))
                    .toSecondOfDay() / 3600.00

            }
            return tiempo.div(60).toString()
        } else {
            return "0"
        }

    }

    fun getTiempoMedio(mainActivity: MainActivity): String {
        if (!mainActivity.user.actividades.isNullOrEmpty()) {
            return tiempo.div(60).div(mainActivity.user.actividades!!.size).toString()
        } else {
            return "0"
        }

    }
}