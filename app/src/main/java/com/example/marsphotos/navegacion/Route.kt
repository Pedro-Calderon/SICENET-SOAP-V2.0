package com.example.marsphotos.navegacion

sealed class Route (val route: String){
    object LoginScreen:Route("LoginScreen")
    object PantallaDos:Route("PantallaDos")

}