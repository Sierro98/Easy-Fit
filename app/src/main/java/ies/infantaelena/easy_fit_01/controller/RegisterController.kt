package ies.infantaelena.easy_fit_01.controller

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import ies.infantaelena.easy_fit_01.data.Usuario


fun makeRegister(
    email: String,
    user: String,
    password: String,
    reppassword: String,
    context: Context,
    nav: Any
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
        builder.setPositiveButton("Aceptar", null)
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
        lateinit var database: DatabaseReference

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    database =
                        FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference().child("users")
                    saveNewUser(email,user,password,database)
                    showAlertCorrect()
                } else {
                    showAlertFail()
                }
            }
    }
}
fun saveNewUser (email:String,user:String,password: String, database:DatabaseReference){
    var id = 0
    val check = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (snapshot in dataSnapshot.children) {
                if (snapshot.key == null) {
                    id =  1;
                } else {
                    id = id + 1;

                }

            }
            Log.d(
                "JOSE",
                id.toString()
            )
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    database.addValueEventListener(check)
    Log.d(
        "JOSE",
        check.toString()
    )
    database.child(id.toString()).setValue(
        Usuario(
            email = email,
            username = user,
            password = password,
            //level = 0f
        )
    )

}
abstract fun incrementCounter(database: DatabaseReference) {
    database.runTransaction(object : Transaction.Handler {
        override fun doTransaction(currentData: MutableData): Transaction.Result {
            if (currentData.value == null) {
                currentData.value = 1
            } else {
                currentData.value = (currentData.value as Long?)!! + 1
            }
            return Transaction.success(currentData)
        }

        fun onComplete(
            firebaseError: FirebaseError?,
            committed: Boolean,
            currentData: DataSnapshot?
        ) {
            if (firebaseError != null) {
                Log.d("Firebase counter increment failed.")
            } else {
                Log.d("Firebase counter increment succeeded.")
            }
        }
    })
}