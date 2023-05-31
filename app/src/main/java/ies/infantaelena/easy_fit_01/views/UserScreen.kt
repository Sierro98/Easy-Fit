package ies.infantaelena.easy_fit_01.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.model.MenuDrawerItems
import ies.infantaelena.easy_fit_01.model.MenuDrawerItemsSpanish
import ies.infantaelena.easy_fit_01.model.MenuItem
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
                },
                showProgress = false,
                mainActivity = mainActivity
            )
        },
        drawerContent = {
            DrawerHeader(
                mainActivity = mainActivity
            )
            DrawerBody(
                items = drawerMenuItems,
                onItemClick = {
                    when (it.id) {
                        "logout" -> {
                            userScreenViewModel.LogOut(navController)
                        }

                        "home" -> {
                            navController.navigate(Screen.MainScreen.route)
                        }

                        "challenges" -> {
                            navController.navigate(Screen.ChallengeScreen.route)
                        }
                    }
                }
            )
        }
    ) { _ ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressBar(
                percentage = userScreenViewModel.getLevel(mainActivity), // TODO: añadir progreso del nivel actual
                number = 100,
                radius = 70.dp,
                strokeWidth = 11.dp
            )
            Text(
                text = stringResource(R.string.userscreen_estadisticas_global),
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium,
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Row() {
                StatsCard(
                    stat = stringResource(R.string.pasos_totales),
                    statContent = userScreenViewModel.getPasosTotales(mainActivity),
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp, end = 2.dp),
                    icon = R.drawable.step_icon
                )
                StatsCard(
                    stat = stringResource(R.string.pasos_medios),
                    statContent = userScreenViewModel.getPasosMedios(mainActivity),
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 2.dp, end = 8.dp),
                    icon = R.drawable.step_icon
                )
            }
            Row() {
                StatsCard(
                    stat = stringResource(R.string.distancia_total),
                    statContent = userScreenViewModel.getKmTotales(mainActivity),
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp, end = 2.dp),
                    icon = R.drawable.map_icon
                )
                StatsCard(
                    stat = stringResource(R.string.distancia_media),
                    statContent = userScreenViewModel.getKmMedios(mainActivity = mainActivity), // TODO: añadir distancia media
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 2.dp, end = 8.dp),
                    icon = R.drawable.map_icon
                )
            }
            Row() {
                StatsCard(
                    stat = stringResource(R.string.tiempo_total),
                    statContent = userScreenViewModel.getTiempoTotal(mainActivity), // TODO: añadir tiempo toal
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp, end = 2.dp),
                    icon = R.drawable.timer_icon
                )
                StatsCard(
                    stat = stringResource(R.string.tiempo_medio),
                    statContent = userScreenViewModel.getTiempoMedio(mainActivity), // TODO: añadir tiempo medio
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 2.dp, end = 8.dp),
                    icon = R.drawable.timer_icon
                )
            }
        }
    }
}


@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = 8.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage =
        animateFloatAsState(
            targetValue = if (animationPlayed) percentage else 0f,
            animationSpec = tween(durationMillis = animationDuration, delayMillis = animationDelay)
        )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${(curPercentage.value * number).toInt()}%",
            color = MaterialTheme.colors.onPrimary,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StatsCard(
    color: Color = MaterialTheme.colors.secondary.copy(alpha = 0.6f),
    stat: String,
    statContent: String,
    modifier: Modifier,
    icon: Int
) {
    Card(
        backgroundColor = color,
        elevation = 0.dp,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Text(text = stat)
            }
            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(modifier = Modifier.padding(5.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(text = statContent)
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

