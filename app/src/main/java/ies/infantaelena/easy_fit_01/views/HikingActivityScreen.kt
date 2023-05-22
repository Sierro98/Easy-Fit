package ies.infantaelena.easy_fit_01.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.services.Polyline
import ies.infantaelena.easy_fit_01.viewmodel.RunActivityScreenViewModel

@Composable
fun HikingActivityScreen(
    runViewModel: RunActivityScreenViewModel = viewModel()
) {
    val context: Context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val pathPoints: List<Polyline> = runViewModel.pathPoints

    runViewModel.subscribe2Observers(lifecycle)

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
                painter = painterResource(id = R.drawable.hikin_man),
                contentDescription = stringResource(id = R.string.activityHiking),
                modifier = Modifier.size(50.dp)
            )
            Text(text = stringResource(id = R.string.activityHiking), fontSize = 30.sp)
        }

        MyGoogleMap(pathPoints = pathPoints, runViewModel = runViewModel)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.timer_icon),
                contentDescription = "Timer Icon",
                modifier = Modifier
                    .weight(1f)
                    .size(30.dp)
            )
            TimerActivity(
                runViewModel = runViewModel,
                modifier = Modifier.weight(3f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.step_icon),
                contentDescription = "Step counter icon",
                modifier = Modifier
                    .weight(1f)
                    .size(30.dp)
            )
            StepCounter(
                runViewModel = runViewModel,
                modifier = Modifier.weight(3f)
            )
        }
        PlayButton(runViewModel = runViewModel, context = context)
    }
}