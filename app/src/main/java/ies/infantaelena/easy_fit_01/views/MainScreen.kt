package ies.infantaelena.easy_fit_01

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.data.Activity
import ies.infantaelena.easy_fit_01.data.ActivityType
import ies.infantaelena.easy_fit_01.data.MenuDrawerItems
import ies.infantaelena.easy_fit_01.data.MiniFloatingActionItem
import ies.infantaelena.easy_fit_01.navigation.Screen
import ies.infantaelena.easy_fit_01.state.FloatingButtonState
import ies.infantaelena.easy_fit_01.views.AppBar
import ies.infantaelena.easy_fit_01.views.DrawerBody
import ies.infantaelena.easy_fit_01.views.DrawerHeader
import kotlinx.coroutines.launch
import java.time.LocalDate
import ies.infantaelena.easy_fit_01.R


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
        //TODO: probando el multipleactionbutton
        floatingActionButton = {
            AddActionMultipleButton(context = context)
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
                                Modifier.size(50.dp)
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

@Composable
fun AddActivityButton(context: Context) {
    // TODO: funcionalidad de llevar a la pagina de actividades
    FloatingActionButton(onClick = {
        Toast.makeText(context, "Añadiendo actividad", Toast.LENGTH_SHORT).show()
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Activity"
        )
    }
}

@Composable
fun AddActionMultipleButton(context: Context) {
    var floatingState by remember {
        mutableStateOf(FloatingButtonState.COLLAPSED)
    }
    MultiFloatingButton(
        context = context,
        floatingState = floatingState,
        onFloatingStateChange = { state ->
            floatingState = state
        },
        items = almacenActividades()
    )
}

@Composable
fun MultiFloatingButton(
    context: Context,
    floatingState: FloatingButtonState,
    onFloatingStateChange: (FloatingButtonState) -> Unit,
    items: List<MiniFloatingActionItem>
) {
    // TODO: ver si se puede mover esta funcionalidad al viewModel
    val transition = updateTransition(targetState = floatingState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate floating button") { state ->
        if (state == FloatingButtonState.EXPANDED) 315f else 0f
    }

    val floatingScale by transition.animateFloat(label = "Mini floating buttons transition") {
        if (it == FloatingButtonState.EXPANDED) 60f else 0f
    }

    val fadeFloating by transition.animateFloat(
        label = "Float fade",
        transitionSpec = { tween(durationMillis = 50) }) {
        if (it == FloatingButtonState.EXPANDED) 1f else 0f
    }

    val fadeText by transition.animateDp(
        label = "Text fade",
        transitionSpec = { tween(durationMillis = 50) }) {
        if (it == FloatingButtonState.EXPANDED) 2.dp else 0.dp
    }


    Column(
        horizontalAlignment = Alignment.End
    ) {
        if (transition.currentState == FloatingButtonState.EXPANDED) {
            items.forEach { item ->
                MiniFloatingActionButtons(
                    item = item,
                    onMinFloatingItemClick = {

                    },
                    fadeFloating = fadeFloating,
                    floatingScale = floatingScale,
                    fadeText = fadeText
                )
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }

        FloatingActionButton(backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                onFloatingStateChange(
                    if (transition.currentState == FloatingButtonState.EXPANDED) {
                        FloatingButtonState.COLLAPSED
                    } else {
                        FloatingButtonState.EXPANDED
                    }
                )
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir Actividad",
                modifier = Modifier.rotate(rotate)
            )
        }
    }
}

@Composable
fun MiniFloatingActionButtons(
    item: MiniFloatingActionItem,
    floatingScale: Float,
    fadeFloating: Float,
    fadeText: Dp,
    onMinFloatingItemClick: (MiniFloatingActionItem) -> Unit
) {

    val buttonColor = MaterialTheme.colors.primary
    val shadow = Color.Black.copy(.5f)
    Row {
        Text(
            text = item.label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .alpha(
                    animateFloatAsState(targetValue = fadeFloating, animationSpec = tween(50)).value
                )
                .shadow(fadeText)
                .background(MaterialTheme.colors.surface)
                .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                .align(CenterVertically)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Canvas(
            modifier = Modifier
                .size(60.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    onClick = {
                        onMinFloatingItemClick.invoke(item)
                    },
                    indication = rememberRipple(
                        bounded = false,
                        radius = 20.dp,
                        color = MaterialTheme.colors.onSurface
                    ),

                    ),
        ) {
            drawCircle(
                color = shadow,
                radius = floatingScale,
                center = Offset(
                    center.x + 2f,
                    center.y + 2f
                )
            )
            drawCircle(
                color = buttonColor,
                radius = floatingScale
            )
            drawImage(
                image = item.icon, topLeft = Offset(
                    center.x - (item.icon.width / 2),
                    center.y - (item.icon.width / 2)
                ),
                alpha = fadeFloating
            )
        }
    }
}

@Composable
fun almacenActividades(): List<MiniFloatingActionItem> {

    val ActivityItems = listOf(
        MiniFloatingActionItem(
            id = 1,
            icon = ImageBitmap.imageResource(id = R.drawable.running_man),
            label = "Correr"
        ),
        MiniFloatingActionItem(
            id = 2,
            icon = ImageBitmap.imageResource(id = R.drawable.walk_man),
            label = "Andar"
        ),
        MiniFloatingActionItem(
            id = 3,
            icon = ImageBitmap.imageResource(id = R.drawable.hikin_man),
            label = "Senderismo"
        ),
        MiniFloatingActionItem(
            id = 4,
            icon = ImageBitmap.imageResource(id = R.drawable.bicycle_man),
            label = "Ciclismo"
        ),
        MiniFloatingActionItem(
            id = 5,
            icon = ImageBitmap.imageResource(id = R.drawable.calisthenics),
            label = "Calistenia"
        ),
        MiniFloatingActionItem(
            id = 6,
            icon = ImageBitmap.imageResource(id = R.drawable.team_sports),
            label = "Varios"
        )
    )
    return ActivityItems
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


