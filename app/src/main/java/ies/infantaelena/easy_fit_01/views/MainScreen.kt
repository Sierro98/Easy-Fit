package ies.infantaelena.easy_fit_01

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.data.Activity
import ies.infantaelena.easy_fit_01.data.ActivityType
import ies.infantaelena.easy_fit_01.data.MenuDrawerItems
import ies.infantaelena.easy_fit_01.navigation.Screen
import ies.infantaelena.easy_fit_01.views.AppBar
import ies.infantaelena.easy_fit_01.views.DrawerBody
import ies.infantaelena.easy_fit_01.views.DrawerHeader
import kotlinx.coroutines.launch
import java.time.LocalDate


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
        // TODO: internacionalizar los items del menu
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = MenuDrawerItems,
                // TODO: hacer todas las redirecciones
                onItemClick = {
                    Toast.makeText(context, "Pulsado ${it.title}", Toast.LENGTH_SHORT).show()
                    when (it.id) {
                        "logout" -> {
                            // TODO: en el caso del logout, hacer logout en la base de datos
                            navController.popBackStack()
                            navController.navigate(Screen.LoginScreen.route)
                        }
                    }
                }
            )
        },
        // TODO: funcionalidad de llevar a la pagina de actividades
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(context, "Añadiendo actividad", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Activity"
                )
            }
        }
    ) { _ ->
        // TODO: Fragmentar las funciones para poder reutilizar y facilidad de lectura y mejorar codigo
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pruebaActividades) { activity ->
                Card(
                    elevation = 0.dp,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = 40.dp,
                            end = 40.dp,
                            top = 20.dp,
                            bottom = 20.dp
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = activity.activityIcon,
                                contentDescription = "icono de actividad",
                                Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(text = activity.activityType.toString())
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = "realizada en: ${activity.date.toString()}")
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = "tiempo: ${activity.time.toDouble().div(3600)} horas")
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "distancia: ${
                                activity.distance?.toDouble()?.div(1000)
                            } kilometros"
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            }
        }
    }
}

var pruebaActividades: List<Activity> = listOf(
    Activity(
        id = 0,
        activityType = ActivityType.RUN,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 1,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.Hiking,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 2,
        activityType = ActivityType.CICLING,
        activityIcon = Icons.Default.SportsMotorsports,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 3,
        activityType = ActivityType.CALISTHENICS,
        activityIcon = Icons.Default.SportsGymnastics,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 4,
        activityType = ActivityType.TEAM_SPORTS,
        activityIcon = Icons.Default.SportsBasketball,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 5,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 6,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 7,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 8,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 9,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 10,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 11,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    ),
    Activity(
        id = 12,
        activityType = ActivityType.HIKING,
        activityIcon = Icons.Default.RunCircle,
        time = 3600,
        distance = 4000,
        date = LocalDate.now(),
        experience = 5
    )
)


