package com.example.marsphotos.ui.screens.loginScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marsphotos.R
import com.example.marsphotos.ui.screens.MarsViewModel
@Composable
fun LoginScreen(viewModel: MarsViewModel) {

    val context= LocalContext.current

    var matricula by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }

    var isValidpassword by remember {
        mutableStateOf(false)
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xf3f8d6))){
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()) {
            Card(Modifier.padding(12.dp),
            shape= RoundedCornerShape(10.dp)

        ) {
            Column (Modifier.padding(16.dp)){
                    RowImagen()
                RowMatricula(
                    matricula=matricula,
                    matriculaChange = {
                        matricula=it
                    }
                    )
                RowPassword(
                    contrasena = password,
                    passwordChange ={password=it
                                    isValidpassword=password.length>=6},
                    passwordVisible = passwordVisible,
                    passwordVisibleChange = { passwordVisible=!passwordVisible },
                    isValidPassword = isValidpassword
                )
                RowButtonLogin(
                    viewModel = viewModel,
                    matricula = matricula,
                    password = password,
                    isValidPassword = isValidpassword
                )            }
        }
        }
    }
}
@Composable
fun RowButtonLogin(
    viewModel: MarsViewModel,
    matricula: String,
    password: String,
    isValidPassword: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isValidPassword) {
                    viewModel.realizarAccesoLogin(matricula, password)
                }
                      },
            enabled = isValidPassword
        ) {
            Text(text = "Inciar Sesión")
        }
    }
}

fun login(context: Context) {
    Toast.makeText(context, "Inicio Sesion", Toast.LENGTH_LONG).show()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowMatricula(
    matricula: String,
    matriculaChange:(String)->Unit,
){
    Row (
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedTextField(value = matricula,
            onValueChange = matriculaChange,
            label={ Text(text = "Matricula")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines =1,
            singleLine = true
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowPassword(
    contrasena: String,
    passwordChange: (String)->Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: ()->Unit,
    isValidPassword: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = contrasena,
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if(passwordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(
                    onClick = passwordVisibleChange
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = "Ver contraseña")
                }
            },
            visualTransformation = if(passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }

        )
    }
}



@Composable
fun RowImagen(){
    Row (
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center){
        Image(
            modifier = Modifier.width(100.dp),
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Imagen Login")
    }
}