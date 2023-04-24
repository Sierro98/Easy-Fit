package ies.infantaelena.easy_fit_01.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val contentDescription: String
)

val MenuDrawerItems = listOf(
    MenuItem(
        id = "home",
        title = "Home",
        contentDescription = "Go to home screen",
        icon = Icons.Default.Home
    ),
    MenuItem(
        id = "user",
        title = "User",
        contentDescription = "Go to user screen",
        icon = Icons.Default.Person
    ),
    MenuItem(
        id = "challenges",
        title = "Challenges",
        contentDescription = "Go to challenges screen",
        icon = Icons.Default.Star
    ),
    MenuItem(
        id = "info",
        title = "Information",
        contentDescription = "Go to information screen",
        icon = Icons.Default.Info
    ),
    MenuItem(
        id = "logout",
        title = "Logout",
        contentDescription = "Logout and go to the login page",
        icon = Icons.Default.Logout
    )
)
