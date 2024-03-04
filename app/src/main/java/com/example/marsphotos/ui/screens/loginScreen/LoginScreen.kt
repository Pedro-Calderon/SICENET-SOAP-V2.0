package com.example.marsphotos.ui.screens.loginScreen

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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marsphotos.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(){

    val context= LocalContext.current

    var matricula by remember {
        mutableStateOf("")
    }

    var isValidMatricula by remember {
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
        .background(Color(0xFFb5e48c))){
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
                RowMatricula()
            }
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowMatricula(
    matricula: String,
    matriculaChange:(String)->Unit,
    isValid:Boolean
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
            singleLine = true,
            colors=if(isValid){
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Green,
                    focusedBorderColor = Color.Green
                )
            }else{
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Red,
                    focusedBorderColor = Color.Red
                )
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