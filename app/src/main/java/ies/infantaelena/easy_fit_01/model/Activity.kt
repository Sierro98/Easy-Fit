package ies.infantaelena.easy_fit_01.model

import ies.infantaelena.easy_fit_01.services.Polyline

data class Activity(
    val activityType: String,
    val time: String,
    val distance: String? = "0",
    val date: String,
    val experience: String,
    var pathPoints: List<Polyline>?
)
