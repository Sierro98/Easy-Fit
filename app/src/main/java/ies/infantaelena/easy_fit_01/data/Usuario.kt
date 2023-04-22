package ies.infantaelena.easy_fit_01.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario(
    val email: String?= null,
    val username: String? = null,
    val password: String? = null,
    //val level: Number?= null,
    //val actividades: List<Activity>?=null
)