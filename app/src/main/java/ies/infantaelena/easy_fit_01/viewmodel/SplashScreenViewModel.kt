package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.Challenge
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen
import ies.infantaelena.easy_fit_01.services.Polyline

class SplashScreenViewModel() : ViewModel() {
    val db: DatabaseReference =
        FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference().child("users")

    fun checkIfLogin(nav: NavController, mainActivity: MainActivity, current: Context) {
        try {
            val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
            if (!user?.uid.isNullOrBlank()) {
                user?.let {
                    db.child(it.uid).get().addOnSuccessListener {
                        if (it.value==null){
                            Thread.sleep(1000)
                            nav.navigate(route = Screen.LoginScreen.route) {
                                popUpTo(route = Screen.SplashScreen.route) {
                                    inclusive = true
                                }
                            }
                        }else{
                        val userContains: HashMap<String, Any> = it.value as HashMap<String, Any>
                        var listActiv: List<Activity>? = null
                        if (userContains.get("actividades") != null) {
                            val listAux = userContains.get("actividades") as List<Any>
                            listActiv = emptyList()
                            for (i in listAux.indices) {
                                var aux = listAux[i] as HashMap<String, String>
                                var listLatLngAux = aux.get("pathPoints") as List<Any>
                                var listLatLng: List<LatLng> = listOf()
                                for (i in listLatLngAux.indices) {
                                    var aux = listLatLngAux[i] as HashMap<String, Double>
                                    val point = LatLng(
                                        aux.get("latitude")!!,
                                        aux.get("longitude")!!
                                    )
                                    listLatLng = listLatLng.plus(point)
                                }
                                var activity = Activity(
                                    aux.get("activityType").toString(),
                                    aux.get("time").toString(),
                                    aux.get("distance").toString(),
                                    aux.get("date").toString(),
                                    aux.get("experience").toString(),
                                    listLatLng
                                )
                                listActiv = listActiv?.plus(activity)
                            }
                        }
                        var listChal: List<Challenge>? = null
                        if (userContains.get("challenges") != null) {
                            listChal = emptyList()
                            val listAux = userContains.get("challenges") as List<Any>
                            for (i in listAux.indices) {
                                var aux = listAux[i] as HashMap<String, String>
                                var challenge = Challenge(
                                    aux.get("id").toString(),
                                    aux.get("challengeType").toString(),
                                    aux.get("challengeContent").toString(),
                                    aux.get("contenidoReto").toString(),
                                    aux.get("challengeComplete").toString().toBoolean(),
                                    aux.get("exp").toString()
                                )
                                listChal = listChal?.plus(challenge)
                            }


                        }

                        mainActivity.user = Usuario(
                            email = userContains.get("email").toString(),
                            username = userContains.get("username").toString(),
                            level = userContains.get("level").toString(),
                            actividades = listActiv as MutableList<Activity>?,
                            challenges = listChal as MutableList<Challenge>?,
                            exp = userContains.get("exp").toString(),
                        )
                        nav.navigate(route = Screen.MainScreen.route) {
                            popUpTo(route = Screen.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                    }
                }

            } else {
                Thread.sleep(1000)
                nav.navigate(route = Screen.LoginScreen.route) {
                    popUpTo(route = Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }catch (ex:Exception){
            Thread.sleep(1000)
            nav.navigate(route = Screen.LoginScreen.route) {
                popUpTo(route = Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}