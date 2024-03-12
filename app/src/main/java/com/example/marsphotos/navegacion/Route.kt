package com.example.marsphotos.navegacion

sealed class Route (val route: String){
    object LoginScreen:Route("LoginScreen")
    object PantallaDos:Route("PantallaDos")
    object Calificaciones:Route("Calificaciones")
    object CalificacionesFinales:Route("CalificacionesF")
    object CargaAcademica:Route("CargaAcademica")
    object Kardex:Route("Kardex")



}