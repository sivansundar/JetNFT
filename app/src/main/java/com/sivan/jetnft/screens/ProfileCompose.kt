package com.sivan.jetnft.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.sivan.jetnft.MainViewModel
import com.sivan.jetnft.ui.theme.JetNFTTheme

class ProfileCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}

@Composable
fun ProfileComposeRootview(mainViewModel: MainViewModel) {
    JetNFTTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Text(text = "Profile",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.SemiBold)
        }
    }}
@Composable
fun Greeting4(name: String) {
    Text(text = "Hello $name!")
}
