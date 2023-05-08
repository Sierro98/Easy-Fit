package ies.infantaelena.easy_fit_01.views

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.state.ActivityState
import ies.infantaelena.easy_fit_01.viewmodel.RunActivityScreenViewModel


// TODO: internacionalizar textos
@Composable
fun RunActivityScreen(
    navController: NavController,
    runViewModel: RunActivityScreenViewModel = viewModel()
) {
    val context = LocalContext.current
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
        val singapore = LatLng(1.35, 103.87)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(10.dp)
                .border(width = 5.dp, color = Color.DarkGray, shape = MaterialTheme.shapes.medium),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
        // TODO: aqui deberan de ir los datos reales
        Text(text = "Tiempo: 1234")
        Text(text = "Pasos: 1234")
        PlayButton(runViewModel = runViewModel, context = context)
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
                        .makeText(context, context.getString(R.string.longPressAction), Toast.LENGTH_SHORT)
                        .show()
                },
                onLongClick = {
                    if (actionState == ActivityState.PLAY) {
                        vibrator.cancel()
                        vibrator.vibrate(vibrationEffect1)
                        actionState = ActivityState.STOP
                    } else {
                        vibrator.cancel()
                        vibrator.vibrate(vibrationEffect1)
                        actionState = ActivityState.PLAY
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