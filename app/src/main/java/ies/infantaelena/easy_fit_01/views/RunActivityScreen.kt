package ies.infantaelena.easy_fit_01.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
                contentDescription = "Icono de hombre corriendo",
                modifier = Modifier.size(50.dp)
            )
            Text(text = "Correr", fontSize = 30.sp)
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

@Composable
fun PlayButton(runViewModel: RunActivityScreenViewModel, context: Context) {
    var actionState by remember { mutableStateOf(ActivityState.PLAY) }
    Button(
        shape = RoundedCornerShape(100),
        onClick = {
            Log.d("VALOR", "VALOR DE LA VARIABLE -> " + runViewModel.contadorPlay)
            if (runViewModel.contadorPlay > 0) {
                if (actionState == ActivityState.PLAY) {
                    actionState = ActivityState.STOP
                } else {
                    actionState = ActivityState.PLAY
                }
                runViewModel.contadorPlay = 0
            } else {
                if (actionState == ActivityState.PLAY) {
                    Toast.makeText(context, "Pulse otra vez para comenzar", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("VALOR", "VALOR DE LA VARIABLE -> " + runViewModel.contadorPlay)
                } else {
                    Toast.makeText(context, "Pulse otra vez para parar", Toast.LENGTH_SHORT).show()
                    Log.d("VALOR", "VALOR DE LA VARIABLE -> " + runViewModel.contadorPlay)
                }
                runViewModel.contadorPlay++
                Log.d("VALOR", "VALOR DE LA VARIABLE -> " + runViewModel.contadorPlay)
            }
        },
        modifier = Modifier.size(100.dp)
    ) {
        if (actionState == ActivityState.STOP) {
            Icon(
                painter = painterResource(id = R.drawable.stop_icon),
                contentDescription = "Stop Activity",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.play_icon),
                contentDescription = "Start Activity",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}