package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen
import ies.infantaelena.easy_fit_01.services.Polyline

class SplashScreenViewModel() : ViewModel() {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val db: DatabaseReference =
        FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference().child("users")

    fun checkIfLogin(nav: NavController, mainActivity: MainActivity, current: Context) {
        if (user != null) {
            user.let {
                db.child(it.uid).get().addOnSuccessListener {
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


                    mainActivity.user = Usuario(
                        email = userContains.get("email").toString(),
                        username = userContains.get("username").toString(),
                        level = Integer.parseInt(
                            userContains.get(
                                "level"
                            ).toString()
                        ),
                        actividades = listActiv as MutableList<Activity>?
                    )
                    nav.navigate(route = Screen.MainScreen.route) {
                        popUpTo(route = Screen.SplashScreen.route) {
                            inclusive = true
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
    }
}