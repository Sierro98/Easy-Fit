package ies.infantaelena.easy_fit_01.data

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate

data class Activity(
    val id: Int,
    val activityType: ActivityType,
    val activityIcon: ImageVector,
    val time: Number,
    val distance: Number? = 0,
    val date: LocalDate,
    val experience: Number
)
data class ActivityBBDD(
    val id: Int,
    val activityType: ActivityType,
    val time: Number,
    val distance: Number? = 0,
    val date: LocalDate,
    val experience: Number
)