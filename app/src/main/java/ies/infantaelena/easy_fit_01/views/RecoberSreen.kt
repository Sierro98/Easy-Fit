package ies.infantaelena.easy_fit_01.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.model.customTextSelectionColors
import ies.infantaelena.easy_fit_01.viewmodel.RecoberViewModel

@Composable
fun RecoberSreen(navController: NavController, recoberViewModel: RecoberViewModel = viewModel()) {
    val context: Context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.padding(top = 60.dp))
        Image(
            painter = painterResource(id = R.drawable.easy_fit_logo),
            contentDescription = R.string.logoDescription.toString(),
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        )
        // Llamada al metodo que contiene el Textfield del email
        LoginEmail(email = recoberViewModel.email, onInputChanged = { recoberViewModel.email = it })
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Spacer(modifier = Modifier.padding(top = 30.dp))
        /*
        Componente Button que maneja el intento de entrada a la aplicacion llamando a la funcion
        recupPass()
         */
        Button(
            onClick = {
                recoberViewModel.recupPass(
                    email = recoberViewModel.email,
                    context = context,
                    nav = navController,
                )

            },
            modifier = Modifier
                .height(50.dp)
                .width(140.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        ) {
            Text(
                text = stringResource(id = R.string.send),
                style = MaterialTheme.typography.button
            )
        }
    }

}
@Composable
fun LoginEmail(email: String, onInputChanged: (String) -> Unit) {
    // funcion usada para envolver al textfield y darle el color que queramos al los text selectors
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            value = email,
            onValueChange = onInputChanged,
            label = {
                Text(
                    text = stringResource(id = R.string.email),
                    color = MaterialTheme.colors.onPrimary
                )
            },
            singleLine = true,
            modifier = Modifier
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent, //hide the indicator
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primary
            )
        )
    }

}