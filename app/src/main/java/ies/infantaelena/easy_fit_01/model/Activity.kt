package ies.infantaelena.easy_fit_01.model

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate

data class Activity(
    val activityType: String,
    val time: String,
    val distance: String? = "0",
    val date: String,
    val experience: String
)
