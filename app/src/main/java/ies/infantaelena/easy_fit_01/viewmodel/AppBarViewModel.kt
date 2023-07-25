package ies.infantaelena.easy_fit_01.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.infantaelena.easy_fit_01.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppBarViewModel() : ViewModel() {
    private var _level = mutableStateOf("")
    var level: State<String> = _level

    private var _exp = mutableStateOf(0F)
    var exp: State<Float> = _exp

    fun getLevelExp(mainActivity: MainActivity) {
        viewModelScope.launch {
            while (true) {
                if (mainActivity.user.exp != "0") {
                    _level.value = mainActivity.user.level.toString()
                    _exp.value = mainActivity.user.exp?.toFloat()?.div(100)!!
                } else {
                    _level.value = "0"
                    _exp.value = 0F
                }
                delay(2000L)
            }

        }
    }

}