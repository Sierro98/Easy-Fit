package ies.infantaelena.easy_fit_01

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.data.MenuItem
import ies.infantaelena.easy_fit_01.views.AppBar
import ies.infantaelena.easy_fit_01.views.DrawerBody
import ies.infantaelena.easy_fit_01.views.DrawerHeader
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = listOf(
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
                    )
                ),
                onItemClick = {
                    Toast.makeText(context, "Pulsado ${it.title}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    ) { paddingValues -> paddingValues.calculateTopPadding()
    }
}
