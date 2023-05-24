package ies.infantaelena.easy_fit_01

import android.Manifest
import android.content.Context
import android.location.Location
import android.util.Log
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import ies.infantaelena.easy_fit_01.model.*
import ies.infantaelena.easy_fit_01.navigation.Screen
import ies.infantaelena.easy_fit_01.state.FloatingButtonState
import ies.infantaelena.easy_fit_01.viewmodel.MainScreenViewModel
import ies.infantaelena.easy_fit_01.views.AppBar
import ies.infantaelena.easy_fit_01.views.DrawerBody
import ies.infantaelena.easy_fit_01.views.DrawerHeader
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale
import kotlin.math.roundToInt


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel = viewModel(),
    mainActivity: MainActivity
) {

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var iconoActividad: Int;
    val language = Locale.getDefault().language
    val drawerMenuItems: List<MenuItem> = if (language == "es") {
        MenuDrawerItemsSpanish
    } else {
        MenuDrawerItems
    }
    // PERMISOS
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            //Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACTIVITY_RECOGNITION,
            //Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        )
    )
    val lifeCycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifeCycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    })
    // END OF PERMISOS
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
                        "logout" -> {
                            mainScreenViewModel.LogOut(navController)
                        }

                        "user" -> {
                            mainScreenViewModel.GoToUserPage(navController)
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
            mainActivity.user.actividades?.let {
                items(it) { activity ->
                    val totalDistanceInKm = mainScreenViewModel.calculateTotalDistance(activity)
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
                                iconoActividad = when (activity.activityType) {
                                    "RUN" -> R.drawable.running_man
                                    "WALK" -> R.drawable.walk_man
                                    "HIKING" -> R.drawable.hikin_man
                                    "CICLING" -> R.drawable.bicycle_man
                                    "CALISTHENICS" -> R.drawable.calisthenics
                                    "TEAM_SPORTS" -> R.drawable.team_sports
                                    else -> R.drawable.running_man
                                }
                                Icon(
                                    painter = painterResource(id = iconoActividad),
                                    contentDescription = stringResource(R.string.activityIconDescription),
                                    Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.padding(20.dp))
                                when (activity.activityType) {
                                    "RUN" -> mainScreenViewModel.tipoActividad =
                                        context.getString(R.string.activityRun)

                                    "WALK" -> mainScreenViewModel.tipoActividad =
                                        context.getString(R.string.activityWalk)

                                    "HIKING" -> mainScreenViewModel.tipoActividad =
                                        context.getString(R.string.activityHiking)

                                    "CICLING" -> mainScreenViewModel.tipoActividad =
                                        context.getString(R.string.activityCiclism)

                                    "CALISTHENICS" -> mainScreenViewModel.tipoActividad =
                                        context.getString(R.string.activityCalisthenics)

                                    "TEAM_SPORTS" -> mainScreenViewModel.tipoActividad =
                                        context.getString(R.string.activityTeamSport)
                                }
                                Text(
                                    text = mainScreenViewModel.tipoActividad,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            //TODO: Aqui van la fecha el tiempo y la distancia de las actividades
                            Spacer(modifier = Modifier.padding(3.dp))
                            Divider(color = Color.Black, thickness = 1.dp)
                            Spacer(modifier = Modifier.padding(3.dp))
                            Text(text = "${stringResource(R.string.activityDate)} ${activity.date.toString()}")
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = "${stringResource(R.string.activitytime)} ${
                                    activity.time
                                }h"
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = "${stringResource(R.string.activityDistance)} ${
                                    totalDistanceInKm
                                }km"
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AddActionMultipleButton(context: Context, navController: NavController) {
    var floatingState by remember {
        mutableStateOf(FloatingButtonState.COLLAPSED)
    }
    MultiFloatingButton(
        context = context,
        floatingState = floatingState,
        onFloatingStateChange = { state ->
            floatingState = state
        },
        items = almacenActividades(),
        navController = navController
    )
}

@Composable
fun MultiFloatingButton(
    context: Context,
    floatingState: FloatingButtonState,
    onFloatingStateChange: (FloatingButtonState) -> Unit,
    items: List<MiniFloatingActionItem>,
    navController: NavController
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
        transitionSpec = { tween(durationMillis = 70) }) {
        if (it == FloatingButtonState.EXPANDED) 1f else 0f
    }

    val fadeText by transition.animateDp(
        label = "Text fade",
        transitionSpec = { tween(durationMillis = 70) }) {
        if (it == FloatingButtonState.EXPANDED) 2.dp else 0.dp
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        if (transition.currentState == FloatingButtonState.EXPANDED) {
            items.forEach { item ->
                MiniFloatingActionButtons(
                    item = item,
                    // TODO: Acciones de los FAB
                    onMinFloatingItemClick = { miniFloatingActionItem ->
                        when (miniFloatingActionItem.id) {
                            1 -> {
                                navController.navigate(Screen.RunActivityScreen.route)
                            }

                            2 -> {
                                navController.navigate(Screen.WalkActivityScreen.route)
                            }

                            3 -> {
                                navController.navigate(Screen.HikingActivityScreen.route)
                            }

                            4 -> {
                                navController.navigate(Screen.CyclingActivityScreen.route)
                            }

                            5 -> {
                                navController.navigate(Screen.CalisthenicsActivityScreen.route)
                            }

                            6 -> {
                                navController.navigate(Screen.TeamActivityScreen.route)
                            }
                        }
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
                contentDescription = stringResource(id = R.string.addActivityDescription),
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
            label = stringResource(id = R.string.activityRun)
        ),
        MiniFloatingActionItem(
            id = 2,
            icon = ImageBitmap.imageResource(id = R.drawable.walk_man),
            label = stringResource(id = R.string.activityWalk)
        ),
        MiniFloatingActionItem(
            id = 3,
            icon = ImageBitmap.imageResource(id = R.drawable.hikin_man),
            label = stringResource(id = R.string.activityHiking)
        ),
        MiniFloatingActionItem(
            id = 4,
            icon = ImageBitmap.imageResource(id = R.drawable.bicycle_man),
            label = stringResource(id = R.string.activityCiclism)
        ),
        MiniFloatingActionItem(
            id = 5,
            icon = ImageBitmap.imageResource(id = R.drawable.calisthenics),
            label = stringResource(id = R.string.activityCalisthenics)
        ),
        MiniFloatingActionItem(
            id = 6,
            icon = ImageBitmap.imageResource(id = R.drawable.team_sports),
            label = stringResource(id = R.string.activityTeamSport)
        )
    )
    return ActivityItems
}



