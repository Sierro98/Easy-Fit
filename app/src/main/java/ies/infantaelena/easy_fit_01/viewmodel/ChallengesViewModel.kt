package ies.infantaelena.easy_fit_01.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.util.Log


class ChallengesViewModel : ViewModel() {
    var tipoActividad: String by mutableStateOf("");

    fun completeChallenge(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Reto")
        builder.setMessage("¿Desea completar el reto?")
        builder.setPositiveButton(
            "Completar"
        ) { _, _ -> updateChallenge() }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    fun updateChallenge() {

    }
}