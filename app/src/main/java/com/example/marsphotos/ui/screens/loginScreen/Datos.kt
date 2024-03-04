package com.example.marsphotos.ui.screens.loginScreen

// En tu archivo .kt correspondiente, por ejemplo, HelloWorldScreen.kt
package com.example.marsphotos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.marsphotos.ui.theme.MarsPhotosTheme

@Composable
fun HelloWorldScreen() {
    // Contenido de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        // Texto "Hola" con el estilo del tema actual
        Text(
            text = "Hola",
            style = MaterialTheme.typography.h4
        )
    }
}

// Vista previa de la pantalla (opcional)
@Preview
@Composable
fun PreviewHelloWorldScreen() {
    MarsPhotosTheme {
        Surface {
            HelloWorldScreen()
        }
    }
}
