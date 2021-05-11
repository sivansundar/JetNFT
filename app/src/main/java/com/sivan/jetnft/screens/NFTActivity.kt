package com.sivan.jetnft.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sivan.jetnft.database.model.NFTModel
import com.sivan.jetnft.screens.ui.theme.JetNFTTheme

class NFTActivity : ComponentActivity() {

    lateinit var nftModel: NFTModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nftModel = intent.getParcelableExtra<NFTModel>("nft_model")!!

        setContent {
            JetNFTTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    NFTImageCard(image = nftModel.nftImage, modifier = Modifier.fillMaxWidth().height(300.dp))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetNFTTheme {
        Greeting("Android")
    }
}