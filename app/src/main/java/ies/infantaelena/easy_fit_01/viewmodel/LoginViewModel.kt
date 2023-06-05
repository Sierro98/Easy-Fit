package ies.infantaelena.easy_fit_01.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.Challenge
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen
import ies.infantaelena.easy_fit_01.services.Polyline


/**
 * Clase con la funcionalidad de LoginScreen
 */
class LoginViewModel() : ViewModel() {

    //Variables para la vista de Login
    var user by mutableStateOf("")
    var password by mutableStateOf("")
    val db: DatabaseReference =
        FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference().child("users")

    /**
     * Metodo que recibe el email y la contraseña y comprueba si existe en la base de datos y si es asi
     * pasa a la siguiente vista que es la del MainScreen
     * @param email email que recibe desde la vista
     * @param contra contraseña que recibe desde la vista
     * @param context
     * @param nav
     */
    fun checkLogin(
        email: String,
        contra: String,
        context: Context,
        nav: NavController,
        activity: MainActivity
    ) {

        if (email.isBlank() || contra.isBlank()) {
            Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
        } else {
            //Intenta logearse en FirebaseAuth con el email y contraseña
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contra)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        FirebaseAuth.getInstance().currentUser.let {
                            it?.let { it1 ->
                                db.child(it1.uid).get().addOnSuccessListener {
                                    val userContains: HashMap<String, Any> =
                                        it.value as HashMap<String, Any>
                                    var listActiv: List<Activity>? = null
                                    if (userContains.get("actividades") != null) {
                                        val listAux = userContains.get("actividades") as List<Any>
                                        listActiv = emptyList()
                                        for (i in listAux.indices) {
                                            var aux = listAux[i] as HashMap<String, String>
                                            var listLatLngAux = aux.get("pathPoints") as List<Any>

                                            var listLatLng: List<LatLng> = listOf()
                                            for (i in listLatLngAux.indices) {

                                                var aux =
                                                    listLatLngAux[i] as HashMap<String, Double>
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
                                    if (userContains.get("challenges")!=null){
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
                                    activity.user = Usuario(
                                        email = userContains.get("email").toString(),
                                        username = userContains.get("username").toString(),
                                        level = userContains.get( "level" ).toString(),
                                        actividades = listActiv as MutableList<Activity>?,
                                        challenges = listChal as MutableList<Challenge>?,
                                        exp = userContains.get("exp").toString(),
                                    )
                                    authenticate(context = context, activity = activity, nav = nav)
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Login fallido", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    var canAuthenticate = false
    lateinit var promptInfo: BiometricPrompt.PromptInfo
    fun setupAuth(context: Context) {

        if (BiometricManager.from(context)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {

            canAuthenticate = true
            promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Autenticacion Biométrica")
                .setSubtitle("Necesaria para el login")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }

    }

    fun authenticate(context: Context, activity: MainActivity, nav: NavController) {
        if (canAuthenticate) {
            BiometricPrompt(activity, ContextCompat.getMainExecutor(context),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
                        nav.popBackStack()
                        nav.navigate(route = Screen.MainScreen.route) {
                            popUpTo(route = Screen.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            ).authenticate(promptInfo)
        } else {
            Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
            nav.popBackStack()
            nav.navigate(route = Screen.MainScreen.route) {
                popUpTo(route = Screen.LoginScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}


