package ies.infantaelena.easy_fit_01.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ies.infantaelena.easy_fit_01.R

@Composable
fun InfoScreen() {
    val bullet = "\u2022"
    val messages = listOf(
        "Alejandro Sierro",
        "Jose Peña",
    )
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 20.sp))
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.easy_fit_logo),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp),
                text = "Trabajo de aplicación deportiva EasyFit de final de grado de Desarrollo de Aplicaciones Multiplataforma.\n\n Realizado por:",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Text(
                buildAnnotatedString {
                    messages.forEach {
                        withStyle(style = paragraphStyle) {
                            append(bullet)
                            append("\t\t")
                            append(it)
                        }
                    }
                },
                fontSize = 20.sp
            )
        }
    }
}