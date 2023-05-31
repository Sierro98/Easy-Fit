package ies.infantaelena.easy_fit_01.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.R


@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    showProgress: Boolean = true,
    mainActivity: MainActivity
) {
    TopAppBar(
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //TODO: añadir tanto en el texto como en la barra de progreso los valores reales del usuario
                if (showProgress) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .height(8.dp),
                        progress = mainActivity.user.level?.toFloat()?.div(500)!!,
                        backgroundColor = Color.White,
                        color = MaterialTheme.colors.primaryVariant,
                        strokeCap = StrokeCap.Round,
                    )
                }
                Text(text = "Nivel de usuario")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.appBarDrawerDescription)
                )
            }
        }
    )
}