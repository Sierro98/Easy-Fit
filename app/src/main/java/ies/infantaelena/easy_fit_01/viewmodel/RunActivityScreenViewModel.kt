package ies.infantaelena.easy_fit_01.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Clase con la funcionalidad de RunActivityScreen
 */
class RunActivityScreenViewModel: ViewModel() {
    var contadorPlay: Int by mutableStateOf(0)

    fun startActivity(){

    }
}