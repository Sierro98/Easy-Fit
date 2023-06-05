package ies.infantaelena.easy_fit_01.model

data class Challenge(
    val id: String,
    val challengeType: String,
    val challengeContent: String,
    val contenidoReto: String,
    var challengeComplete: Boolean,
    val exp: String
)