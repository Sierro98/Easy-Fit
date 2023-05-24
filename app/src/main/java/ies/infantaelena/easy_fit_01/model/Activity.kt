package ies.infantaelena.easy_fit_01.model

import com.google.android.gms.maps.model.LatLng

data class Activity(
    val activityType: String,
    val time: String,
    val distance: String? = "0",
    val date: String,
    val experience: String,
    var pathPoints: List<LatLng>
)
