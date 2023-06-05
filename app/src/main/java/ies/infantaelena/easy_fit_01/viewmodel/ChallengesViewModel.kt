package ies.infantaelena.easy_fit_01.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.model.Challenge
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen


class ChallengesViewModel : ViewModel() {

    fun completeChallenge(context: Context, index: Int, mainActivity: MainActivity) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Reto")
        builder.setMessage("¿Desea completar el reto?")
        builder.setPositiveButton(
            "Completar"
        ) { _, _ -> updateChallenge(index, mainActivity = mainActivity) }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    fun updateChallenge(index: Int,mainActivity: MainActivity) {
       var challenge: Challenge? = mainActivity.user.challenges?.get(index)
        var exp = 0.0
        if (challenge!=null){
            exp = challenge.exp.toDouble()
            challenge.challengeComplete = true
        }
        if (challenge != null) {
            mainActivity.user.challenges?.set(index, challenge)
        }
        val database: DatabaseReference =
            FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("users")
        val useruid: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        //Guardado de datos en la Raltime Database
        var auxUse: Double? = mainActivity.user.exp?.toDouble()?.plus(exp)
        var auxLv: Int = 0
        if (auxUse != null) {
            if (auxUse >= 100.00){
                auxUse -= 100
                auxLv = 1
            }
        }
        val updatedUser = Usuario(
            actividades = mainActivity.user.actividades,
            email = mainActivity.user.email,
            level = mainActivity.user.level?.toInt()?.plus(auxLv).toString(),
            exp = auxUse.toString(),
            username = mainActivity.user.username,
            challenges = mainActivity.user.challenges
        )
        mainActivity.user = updatedUser
        database.child(useruid?.uid.toString()).setValue(
            updatedUser
        )
    }

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
}