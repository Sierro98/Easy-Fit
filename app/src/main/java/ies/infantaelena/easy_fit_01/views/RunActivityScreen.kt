package ies.infantaelena.easy_fit_01.views

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.other.Constants
import ies.infantaelena.easy_fit_01.services.Polyline
import ies.infantaelena.easy_fit_01.services.TrackingService
import ies.infantaelena.easy_fit_01.state.ActivityState
import ies.infantaelena.easy_fit_01.viewmodel.RunActivityScreenViewModel

// TODO: Mirar funcionalidad del botón
// TODO: internacionalizar textos
@Composable
fun RunActivityScreen(
    navController: NavController,
    runViewModel: RunActivityScreenViewModel = viewModel()
) {
    val context: Context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val pathPoints: List<Polyline> = runViewModel.pathPoints

    TrackingService.pathPoints.observe(lifecycle, Observer {
        runViewModel.addPathPoint(it.last())
    })


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.running_man),
                contentDescription = stringResource(id = R.string.runningIconDescription),
                modifier = Modifier.size(50.dp)
            )
            Text(text = stringResource(id = R.string.activityRun), fontSize = 30.sp)
        }

        MyGoogleMap(pathPoints = pathPoints, runViewModel = runViewModel)

        // TODO: aqui deberan de ir los datos reales
        Text(
            text = "00:00:00",
            color = MaterialTheme.colors.onPrimary,
            fontSize = 40.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        PlayButton(runViewModel = runViewModel, context = context)
    }
}

@Composable
fun MyGoogleMap(pathPoints: List<Polyline>, runViewModel: RunActivityScreenViewModel) {
    val cameraPositionState: CameraPositionState
    if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                pathPoints.last().toList().last(),
                Constants.MAP_ZOOM
            )
        }
    } else {
        cameraPositionState = rememberCameraPositionState()
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(10.dp)
            .border(
                width = 5.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = MaterialTheme.shapes.medium
            ),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            scrollGesturesEnabled = false,
            compassEnabled = false,
            rotationGesturesEnabled = false,
            indoorLevelPickerEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false,
            tiltGesturesEnabled = false
        ),
    ) {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            runViewModel.updatePosition(
                cameraPositionState = cameraPositionState,
                pathPoints = pathPoints
            )
            Polyline(
                points = pathPoints.last().toList(),
                color = MaterialTheme.colors.primaryVariant,
                width = Constants.POLYLINE_WIDTH
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayButton(runViewModel: RunActivityScreenViewModel, context: Context) {
    var actionState by remember { mutableStateOf(ActivityState.PLAY) }
    val buttonColorPlay = MaterialTheme.colors.primary
    val buttonColorStop = MaterialTheme.colors.secondaryVariant
    val shadow = Color.Black.copy(.5f)
    val stopIcon: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.stop_icon)
    val playIcon: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.play_icon)
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val vibrationEffect1: VibrationEffect =
        VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                onClick = {
                    Toast
                        .makeText(
                            context,
                            context.getString(R.string.longPressAction),
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                onLongClick = {
                    if (actionState == ActivityState.PLAY) {
                        vibrator.cancel()
                        vibrator.vibrate(vibrationEffect1)
                        actionState = ActivityState.STOP
                        runViewModel.startStopActivity(
                            context = context,
                            Constants.ACTION_START_SERVICE
                        )

                    } else {
                        vibrator.cancel()
                        vibrator.vibrate(vibrationEffect1)
                        actionState = ActivityState.PLAY
                        runViewModel.startStopActivity(
                            context = context,
                            Constants.ACTION_STOP_SERVICE
                        )
                    }
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
            radius = 100f,
            center = Offset(
                center.x + 2f,
                center.y + 2f
            )
        )
        if (actionState == ActivityState.STOP) {
            drawCircle(
                color = buttonColorStop,
                radius = 100f
            )
        } else {
            drawCircle(
                color = buttonColorPlay,
                radius = 100f
            )
        }
        if (actionState == ActivityState.STOP) {
            scale(scale = 2.5f) {
                drawImage(
                    image = stopIcon,
                    topLeft = Offset(
                        center.x - (stopIcon.width / 2),
                        center.y - (stopIcon.width / 2)
                    ),
                    alpha = 1f,
                )
            }
        } else {
            scale(scale = 2.5f) {
                drawImage(
                    image = playIcon, topLeft = Offset(
                        center.x - (playIcon.width / 2),
                        center.y - (playIcon.width / 2)
                    ),
                    style = Fill
                )
            }
        }
    }
}