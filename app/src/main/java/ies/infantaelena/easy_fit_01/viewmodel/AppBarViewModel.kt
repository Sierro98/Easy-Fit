package ies.infantaelena.easy_fit_01.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import ies.infantaelena.easy_fit_01.MainActivity

class AppBarViewModel() : ViewModel() {
    fun getLevel(mainActivity: MainActivity): String {
        Log.d("PRUEBA", "Metodo getLevel AppBar-> ${mainActivity.user.level.toString()}")
        return if (mainActivity.user.exp != "0") {
            mainActivity.user.level.toString()
        } else {
            "0"
        }
    }

    fun getExp(mainActivity: MainActivity): Float {
        Log.d("PRUEBA", "Metodo getExp AppBar -> ${mainActivity.user.exp?.toFloat()?.div(100)!!}")
        return if (mainActivity.user.exp != "0") {
            mainActivity.user.exp?.toFloat()?.div(100)!!
        } else {
            0F
        }
    }
}