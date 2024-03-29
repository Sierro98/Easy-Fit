package ies.infantaelena.easy_fit_01.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.Challenge
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen
import java.time.LocalDate

/**
 * Clase con la funcionalidad de RegisterScreen
 */
class RegisterViewModel() : ViewModel() {
    var emailValue: String by mutableStateOf("")
    var userValue: String by mutableStateOf("")
    var passwordValue: String by mutableStateOf("")
    var reppasswordValue: String by mutableStateOf("")
    val regex = "^[a-zA-Z0-9]*\$".toRegex()
    val regexContra = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*_-]).{6,}\$".toRegex()

    /**
     * Funcion que se encarga del registro en el RegisterScreen
     *
     * @param email email que se recibe desde la vista
     * @param user  nombre de usuario que se recibe desde la vista
     * @param password contraseña que se recibe desde la vista
     * @param reppassword segunda contraseña que se recibe desde la vista y debe ser igual a la primerA
     * @param context
     * @param nav
     */
    fun makeRegister(
        email: String,
        user: String,
        password: String,
        reppassword: String,
        context: Context,
        nav: NavController
    ) {
        /**
         * Funcion que muestra un alert en caso de que falle el registro en la BBDD
         */
        fun showAlertFail() {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage(context.getString(R.string.alert_error_register))
            builder.setPositiveButton(context.getString(R.string.alert_accept), null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
            //TODO: hacer el string de los alerts
        }

        /**
         * Funcion que muestra un alert en caso de que el registro se complete correctamente
         */
        fun showAlertCorrect() {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.alert_success))
            builder.setMessage(context.getString(R.string.alert_succes_register))
            builder.setPositiveButton(context.getString(R.string.alert_accept), DialogInterface.OnClickListener { dialog, which ->
                nav.popBackStack()
                nav.navigate(route = Screen.LoginScreen.route)
            })
            val dialog: AlertDialog = builder.create()
            dialog.show()
            //TODO: hacer el string de los alerts
        }

        /**
         * Funcion que muestra un alert en caso de que la contraseña no cumpla los parametros
         */
        fun showAlertWrongPassword() {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage(context.getString(R.string.alert_password_error))
            builder.setPositiveButton(context.getString(R.string.alert_accept), DialogInterface.OnClickListener { dialog, which ->

            })
            val dialog: AlertDialog = builder.create()
            dialog.show()
            //TODO: hacer el string de los alerts
        }

        /**
         * Funcion que se encarga de guardar el Usuario en la BBDD
         */
        fun saveInDatabase() {
            // Declaracion de la referencia a la base de datos
            var database: DatabaseReference =
                FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference().child("users")
            //Creacion del Usuario en Firebase Autentication
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var useruid: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                        //Guardado de datos en la Raltime Database
                        database.child(useruid?.uid.toString()).setValue(
                            Usuario(
                                email = email,
                                username = user,
                                level = "0",
                                exp = "0",
                                actividades = mutableListOf(),
                                challenges = mutableListOf(
                                    Challenge("1", "RUN", "Run 20km", "Correr 20km", false, "50"),
                                    Challenge(
                                        "2",
                                        "HIKING",
                                        "Walk through the mountain 20km",
                                        "Andar por la montaña",
                                        false,
                                        "50"
                                    ),
                                    Challenge("3", "WALK", "Walk 20km", "Andar 20km", false, "50"),
                                    Challenge(
                                        "4",
                                        "CICLING",
                                        "Cycle 20km",
                                        "Pedalear 20km",
                                        false,
                                        "50"
                                    ),
                                    Challenge(
                                        "5",
                                        "RUN",
                                        "Run 5km",
                                        "Corre 5km",
                                        false,
                                        "10"
                                    ),
                                    Challenge(
                                        "6",
                                        "RUN",
                                        "Run 10km",
                                        "Corre 10km",
                                        false,
                                        "20"
                                    ),
                                    Challenge(
                                        "7",
                                        "RUN",
                                        "Run 15km",
                                        "Corre 15km",
                                        false,
                                        "30"
                                    ),
                                    Challenge(
                                        "8",
                                        "WALK",
                                        "Walk 10km",
                                        "Anda 10km",
                                        false,
                                        "20"
                                    ),
                                    Challenge(
                                        "9",
                                        "WALK",
                                        "Walk 5km",
                                        "Anda 5km",
                                        false,
                                        "10"
                                    ),
                                    Challenge(
                                        "10",
                                        "WALK",
                                        "Walk 15km",
                                        "Anda 15km",
                                        false,
                                        "30"
                                    ),
                                    Challenge(
                                        "11",
                                        "HIKING",
                                        "Walk through the mountain 5km",
                                        "Andar por la montaña 5km",
                                        false,
                                        "10"
                                    ),
                                    Challenge(
                                        "12",
                                        "HIKING",
                                        "Walk through the mountain 10km",
                                        "Andar por la montaña 10km",
                                        false,
                                        "20"
                                    ),
                                    Challenge(
                                        "13",
                                        "HIKING",
                                        "Walk through the mountain 15km",
                                        "Andar por la montaña 15km",
                                        false,
                                        "30"
                                    ),
                                    Challenge(
                                        "14",
                                        "CICLING",
                                        "Cycle 30km",
                                        "Pedalear 30km",
                                        false,
                                        "55"
                                    ),
                                    Challenge(
                                        "15",
                                        "CICLING",
                                        "Cycle 10km",
                                        "Pedalear 10km",
                                        false,
                                        "10"
                                    ),
                                    Challenge(
                                        "16",
                                        "CICLING",
                                        "Cycle 50km",
                                        "Pedalear 50km",
                                        false,
                                        "60"
                                    ),
                                )
                            )
                        )
                        showAlertCorrect()
                    } else {
                        showAlertFail()
                    }
                }
        }


        //Comprobaciones para guardar en la BBDD
        if (email.isBlank() || user.isBlank() || password.isBlank() || reppassword.isBlank()) {
            Toast.makeText(context, context.getString(R.string.toast_fill_login), Toast.LENGTH_SHORT).show()
            //TODO: Hay que hcaer los string de los toast
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, context.getString(R.string.toast_error_email_format), Toast.LENGTH_SHORT).show()
        } else if (!regex.containsMatchIn(user)) {
            Toast.makeText(context, context.getString(R.string.toast_error_user_format), Toast.LENGTH_SHORT).show()
        } else if (!regexContra.containsMatchIn(password)) {
            showAlertWrongPassword()
        } else if (!password.equals(reppassword)) {
            Toast.makeText(context, context.getString(R.string.toast_pwd_not_match), Toast.LENGTH_SHORT).show()
        } else {
            saveInDatabase()
        }
    }


}
