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
import ies.infantaelena.easy_fit_01.model.Activity
import ies.infantaelena.easy_fit_01.model.ActivityType
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
            builder.setMessage("Se ha producido un error en el registro")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
            //TODO: hacer el string de los alerts
        }

        /**
         * Funcion que muestra un alert en caso de que el registro se complete correctamente
         */
        fun showAlertCorrect() {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Exito")
            builder.setMessage("Se ha registrado con exito")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
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
            builder.setMessage("Las contraseñas debe tener al menos 6 caracteres y un caracter especial (#?!@$%^&*_-)")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->

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
                                level = 0f,
                                actividades = listOf(
                                    Activity(
                                        activityType = ActivityType.RUN,
                                        time = 3600,
                                        distance = 4000,
                                        date = LocalDate.now().toString(),
                                        experience = 5
                                    ),
                                    Activity(
                                        activityType = ActivityType.HIKING,
                                        time = 3600,
                                        distance = 4000,
                                        date = LocalDate.now().toString(),
                                        experience = 5
                                    ),
                                    Activity(
                                        activityType = ActivityType.CICLING,
                                        time = 3600,
                                        distance = 4000,
                                        date = LocalDate.now().toString(),
                                        experience = 5
                                    ),
                                    Activity(
                                        activityType = ActivityType.CALISTHENICS,
                                        time = 3600,
                                        distance = 4000,
                                        date = LocalDate.now().toString(),
                                        experience = 5
                                    ),
                                    Activity(
                                        activityType = ActivityType.TEAM_SPORTS,
                                        time = 3600,
                                        distance = 4000,
                                        date = LocalDate.now().toString(),
                                        experience = 5
                                    ),
                                    Activity(
                                        activityType = ActivityType.HIKING,
                                        time = 3600,
                                        distance = 4000,
                                        date = LocalDate.now().toString(),
                                        experience = 5
                                    )
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
            Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
            //TODO: Hay que hcaer los string de los toast
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Fomato email incorrecto", Toast.LENGTH_SHORT).show()
        } else if (!regex.containsMatchIn(user)) {
            Toast.makeText(context, "Fomato user incorrecto", Toast.LENGTH_SHORT).show()
        } else if (!regexContra.containsMatchIn(password)) {
            showAlertWrongPassword()
        } else if (!password.equals(reppassword)) {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        } else {
            saveInDatabase()
        }
    }


}
