package com.example.marsphotos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marsphotos.ui.screens.MarsViewModel
import com.example.marsphotos.ui.screens.loginScreen.LoginScreen
import com.example.marsphotos.ui.theme.MarsPhotosTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MarsPhotosTheme {
                val marsViewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LoginScreen(viewModel = marsViewModel)
                }
            }
        }
    }
}
