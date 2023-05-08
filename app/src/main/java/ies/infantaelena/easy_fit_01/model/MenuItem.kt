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

val MenuDrawerItemsSpanish = listOf(
    MenuItem(
        id = "home",
        title = "Pantalla principal",
        contentDescription = "Ir a la pagina principal",
        icon = Icons.Default.Home
    ),
    MenuItem(
        id = "user",
        title = "Perfil de usuario",
        contentDescription = "Ir al perfil de usuario",
        icon = Icons.Default.Person
    ),
    MenuItem(
        id = "challenges",
        title = "Retos",
        contentDescription = "Ir a la pagina de retos",
        icon = Icons.Default.Star
    ),
    MenuItem(
        id = "info",
        title = "Información",
        contentDescription = "Ir a la pagina de información",
        icon = Icons.Default.Info
    ),
    MenuItem(
        id = "logout",
        title = "Cerrar sesión",
        contentDescription = "Cerrar sesión",
        icon = Icons.Default.Logout
    )
)
