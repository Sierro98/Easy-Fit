package ies.infantaelena.easy_fit_01.model

import com.google.firebase.database.IgnoreExtraProperties
import java.time.LocalDate

@IgnoreExtraProperties
data class Usuario(
    val email: String? = null,
    val username: String? = null,
    val level: Number? = null,
    val actividades: List<Activity>? = null
)