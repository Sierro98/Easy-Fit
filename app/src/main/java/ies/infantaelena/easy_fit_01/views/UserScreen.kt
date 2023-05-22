package ies.infantaelena.easy_fit_01.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.AddActionMultipleButton
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.model.MenuDrawerItems
import ies.infantaelena.easy_fit_01.model.MenuDrawerItemsSpanish
import ies.infantaelena.easy_fit_01.model.MenuItem
import ies.infantaelena.easy_fit_01.model.Usuario
import ies.infantaelena.easy_fit_01.navigation.Screen
import ies.infantaelena.easy_fit_01.viewmodel.UserScreenViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun UserScreen(
    navController: NavController,
    mainActivity: MainActivity,
    userScreenViewModel: UserScreenViewModel = viewModel()
) {
    val user: Usuario = mainActivity.user
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val language = Locale.getDefault().language
    val drawerMenuItems: List<MenuItem> = if (language == "es") {
        MenuDrawerItemsSpanish
    } else {
        MenuDrawerItems
    }
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
            DrawerHeader(
                mainActivity = mainActivity
            )
            DrawerBody(
                items = drawerMenuItems,
                // TODO: hacer todas las redirecciones
                onItemClick = {
                    when (it.id) {
                        "home" -> {
                            navController.navigate(Screen.MainScreen.route)
                        }
                        "logout" -> {
                            userScreenViewModel.LogOut(navController)
                        }

                        "user" -> {
                            userScreenViewModel.GoToUserPage(navController)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            AddActionMultipleButton(context = context, navController = navController)
        }
    ) { _ ->
        // TODO: Fragmentar las funciones para poder reutilizar y facilidad de lectura y mejorar codigo
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        }
    }


}