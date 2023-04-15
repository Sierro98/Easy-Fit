package ies.infantaelena.easy_fit_01

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import ies.infantaelena.easy_fit_01.data.Usuario
import ies.infantaelena.easy_fit_01.model.customTextSelectionColors
import ies.infantaelena.easy_fit_01.navigation.Screen
import kotlinx.coroutines.delay

/**
 * Funcion Principal de la interfaz de Login en la cual llamamos a los diferentes composables.
 * Posee las variables del nombre de usuario, de la contrasenia y del context actual.
 */


@Composable
fun LoginScreen(navController: NavController) {

    var userValue: String by rememberSaveable { mutableStateOf("") }
    var passwordValue: String by rememberSaveable { mutableStateOf("") }
    val context: Context = LocalContext.current
    /*
    Componente column que ocupa toda la pantalla del dispositivo, en este elemento iran
    anidados el resto de componentes.
     */
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
        // Llamada al metodo que contiene el Textfield del nombre de usuario
        LoginName(usuario = userValue, onInputChanged = { userValue = it })
        Spacer(modifier = Modifier.padding(top = 30.dp))
        // Llamada al metodo que contiene el Textfield de la contrasenia
        LoginPassword(contra = passwordValue, onInputChanged = { passwordValue = it })
        Spacer(modifier = Modifier.padding(top = 30.dp))
        /*
        Componente Button que maneja el intento de entrada a la aplicacion llamando a la funcion
        checkLogin()
         */
        Button(
            onClick = {
                checkLogin(
                    usuario = userValue,
                    contra = passwordValue,
                    context = context,
                    nav = navController
                )
            },
            modifier = Modifier
                .height(50.dp)
                .width(140.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = Modifier.padding(top = 40.dp))
        Text(text = stringResource(R.string.register),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 80.dp)
                .clickable {
                    navController.navigate(Screen.RegisterScreen.route)
                })
    }
}

/**
 * Funcion composable que posee el campo de introduccion de nombre de usuario
 */
@Composable
fun LoginName(usuario: String, onInputChanged: (String) -> Unit) {
    // funcion usada para envolver al textfield y darle el color que queramos al los text selectors
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            value = usuario,
            onValueChange = onInputChanged,
            label = {
                Text(
                    text = stringResource(id = R.string.user),
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

/**
 * Funcion composable que posee el campo de introduccion de contraseña
 */
@Composable
fun LoginPassword(contra: String, onInputChanged: (String) -> Unit) {
    //Variable para saber si queremos mostrar o no la contrasenia
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            value = contra,
            onValueChange = onInputChanged,
            label = {
                Text(
                    text = stringResource(id = R.string.password),
                    color = MaterialTheme.colors.onPrimary,
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
                cursorColor = MaterialTheme.colors.primary,
            ),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Localizacion para accesivilidad
                val description =
                    if (passwordVisible) stringResource(id = R.string.hidePasswordDescription)
                    else stringResource(id = R.string.showPasswordDescription)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
    }
}

var isCorrect: Boolean = false
fun checkLogin(usuario: String, contra: String, context: Context, nav: NavController) {
    // Declaracion de la referencia a la base de datos
    lateinit var database: DatabaseReference

    if (usuario.isBlank() || contra.isBlank()) {
        Toast.makeText(context, "Rellene los campos", Toast.LENGTH_SHORT).show()
    } else {
        val user = hashMapOf(
            "nombre" to usuario,
            "password" to contra
        )
        database =
            FirebaseDatabase.getInstance("https://entornopruebas-c7005-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var a = 0
                for (snapshot in dataSnapshot.children) {
                    val usu = snapshot.getValue<Usuario>()
                    if (usu != null) {
                        isCorrect = true
                        if (usu.username.equals(usuario) and usu.password.equals(contra)) {
                            Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
                            a = 1;
                            nav.popBackStack()
                            nav.navigate(route = Screen.MainScreen.route)
                        }
                    }
                }
                if (a == 0) {
                    Toast.makeText(context, "Login fallido", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.child("users").addValueEventListener(postListener)
    }
}

