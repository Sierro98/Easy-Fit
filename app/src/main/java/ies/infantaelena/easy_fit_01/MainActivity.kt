package ies.infantaelena.easy_fit_01

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ies.infantaelena.easy_fit_01.ui.theme.Easy_fit_01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Easy_fit_01Theme {
                LoginForm()
            }
        }
    }
}

@Preview
@Composable
fun LoginForm() {

    var userValue: String by rememberSaveable { mutableStateOf("") }
    var passwordValue: String by rememberSaveable { mutableStateOf("") }
    val context: Context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.padding(top = 60.dp))
        Image(
            painter = painterResource(id = R.drawable.login_img),
            contentDescription = "Strongman image",
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        )
        // Nombre de Usuario
        LoginName(usuario = userValue, onInputChanged = { userValue = it })
        Spacer(modifier = Modifier.padding(top = 30.dp))
        // Contrasenia
        Prueba01(contra = passwordValue, onInputChanged = { passwordValue = it })
        //LoginPassword(contra = passwordValue, onInputChanged = { passwordValue = it })
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Button(
            onClick = {
                checkLogin(
                    usuario = userValue,
                    contra = passwordValue,
                    context = context
                )
            },
            modifier = Modifier
                .height(50.dp)
                .width(130.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSecondary)
        ) {
            Text(text = "Login")
        }
    }
}

@Composable
fun LoginName(usuario: String, onInputChanged: (String) -> Unit) {
    TextField(
        value = usuario,
        onValueChange = onInputChanged,
        label = {
            Text(
                text = "Usuario",
                color = Color.Black
            )
        },
        modifier = Modifier
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent, //hide the indicator
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onSecondary
        )
    )
}

@Composable
fun LoginPassword(contra: String, onInputChanged: (String) -> Unit) {
    TextField(
        value = contra,
        onValueChange = onInputChanged,
        label = {
            Text(
                text = "Contraseña",
                color = Color.Black,
            )
        },
        singleLine = true,
        modifier = Modifier
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onSecondary,

            )
    )
}

fun checkLogin(usuario: String, contra: String, context: Context) {
    if (usuario.isBlank() || contra.isBlank()) {
        Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
    } else {
        if (usuario == "pruba" && contra == "pruba") {
            Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Login Fallido", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Prueba01(contra: String, onInputChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = contra,
        onValueChange = onInputChanged,
        label = {
            Text(
                text = "Contraseña",
                color = Color.Black,
            )
        },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onSecondary,
            ),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
}
