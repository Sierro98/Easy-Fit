package ies.infantaelena.easy_fit_01.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.model.Challenge
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen


class ChallengesViewModel : ViewModel() {
    fun completeChallenge(
        context: Context,
        challenge: Challenge,
        mainActivity: MainActivity,
        nav: NavController
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.alert_dialog_challenge_title))
        builder.setMessage(context.getString(R.string.alert_dialog_challenge_content))
        builder.setPositiveButton(
            context.getString(R.string.alert_dialog_complete)
        ) { _, _ -> updateChallenge(challenge = challenge, mainActivity = mainActivity, nav = nav) }
        builder.setNegativeButton(context.getString(R.string.alert_dialog_cancel), null)
        builder.show()
    }

    private fun updateChallenge(
        challenge: Challenge,
        mainActivity: MainActivity,
        nav: NavController
    ) {
        var exp = 0.0
        for (i in mainActivity.user.challenges!!) {
            if (i.id == challenge.id) {
                exp = i.exp.toDouble()
                i.challengeComplete = true
            }
        }
        val database: DatabaseReference =
            FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("users")
        val useruid: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        //Guardado de datos en la Raltime Database
        var auxUse: Double? = mainActivity.user.exp?.toDouble()?.plus(exp)
        var auxLv: Int = 0
        if (auxUse != null) {
            if (auxUse >= 100.00) {
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
        nav.popBackStack()
        nav.navigate(Screen.ChallengeScreen.route)
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