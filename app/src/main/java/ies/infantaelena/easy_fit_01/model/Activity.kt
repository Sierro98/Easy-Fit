package ies.infantaelena.easy_fit_01.model

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate

data class Activity(
    val activityType: ActivityType,
    val time: Number,
    val distance: Number? = 0,
    val date: String,
    val experience: Number
)
