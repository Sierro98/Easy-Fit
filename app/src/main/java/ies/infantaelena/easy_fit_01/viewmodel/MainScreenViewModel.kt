package ies.infantaelena.easy_fit_01.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen

/**
 * Clase con la funcionalidad de MainScreen
 */
class MainScreenViewModel() : ViewModel() {
    val user:Usuario = getUsuario()
    var tipoActividad: String by mutableStateOf("");

    /**
     * Funcion que se encarga de deslogear el usuario de Firebase Authentication
     */
    fun LogOut(nav: NavController) {
        try {
            FirebaseAuth.getInstance().signOut()
            nav.popBackStack()
            nav.navigate(route = Screen.LoginScreen.route)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    fun getUsuario(): Usuario {
        var db: DatabaseReference =
            FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("users")
        var useraux = Usuario()
        FirebaseAuth.getInstance().currentUser?.let {
            db.child(it.uid).get().addOnSuccessListener {
                val userContains: HashMap<String, Any> = it.value as HashMap<String, Any>
              useraux = Usuario(
                    email = userContains.get("email").toString(),
                    username = userContains.get("username").toString(),
                    level = Integer.parseInt(
                        userContains.get(
                            "level"
                        ).toString()),
                    actividades = userContains.get("actividades") as List<Activity>?
                )

            }
        }
        return useraux
    }

}