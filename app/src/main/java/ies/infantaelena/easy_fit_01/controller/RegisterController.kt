package ies.infantaelena.easy_fit_01.controller

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.SportsMotorsports
import androidx.navigation.NavController
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import ies.infantaelena.easy_fit_01.data.Activity
import ies.infantaelena.easy_fit_01.data.ActivityBBDD
import ies.infantaelena.easy_fit_01.data.ActivityType
import ies.infantaelena.easy_fit_01.data.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen
import java.time.LocalDate


fun makeRegister(
    email: String,
    user: String,
    password: String,
    reppassword: String,
    context: Context,
    nav: NavController
) {
    fun showAlertFail() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en el registro")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
        //TODO: hacer el string de los alerts
    }

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

    val regex = "^[a-zA-Z0-9]*\$".toRegex()

    if (email.isBlank() || user.isBlank() || password.isBlank() || reppassword.isBlank()) {
        Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
        //TODO: Hay que hcaer los string de los toast
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        Toast.makeText(context, "Fomato email incorrecto", Toast.LENGTH_SHORT).show()
    } else if (!regex.containsMatchIn(user)) {
        Toast.makeText(context, "Fomato user incorrecto", Toast.LENGTH_SHORT).show()
    } else if (password.length < 6) {
        Toast.makeText(context, "Las contraseñas debe tener 6 caracteres", Toast.LENGTH_SHORT)
            .show()
    } else if (!password.equals(reppassword)) {
        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
    } else {
        // Declaracion de la referencia a la base de datos
        var database: DatabaseReference =
            FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("users")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var useruid: FirebaseUser? = FirebaseAuth.getInstance().currentUser

                    database.child(useruid?.uid.toString()).setValue(
                        Usuario(
                            email = email,
                            username = user,
                            password = password,
                            level = 0f,
                        )
                    )

                    showAlertCorrect()
                } else {
                    showAlertFail()
                }
            }

    }
}