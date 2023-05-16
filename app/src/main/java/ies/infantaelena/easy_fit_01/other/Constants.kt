package ies.infantaelena.easy_fit_01.other

import android.graphics.Color


object Constants {

    const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
    const val ACTION_STOP_SERVICE =  "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING = "ACTION_SHOW_TRACKING"

    const val LOCATION_UPDATE_INTERVAL = 2000L
    const val FASTEST_LOCATION_INTERVAL = 1000L

    const val POLYLINE_COLOR = Color.GREEN
    const val POLYLINE_WIDTH = 10f
    const val MAP_ZOOM = 17f

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "tracking"
    const val NOTIFICATION_ID = 1
}