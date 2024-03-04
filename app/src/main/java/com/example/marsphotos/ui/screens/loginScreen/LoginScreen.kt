package com.example.marsphotos.ui.screens.loginScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(){

    val context= LocalContext.current

    var matricula by remember {
        mutableStateOf("")
    }

}