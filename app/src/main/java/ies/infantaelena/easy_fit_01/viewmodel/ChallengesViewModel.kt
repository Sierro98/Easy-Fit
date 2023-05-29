package ies.infantaelena.easy_fit_01.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ChallengesViewModel : ViewModel() {
    var tipoActividad: String by mutableStateOf("");

}