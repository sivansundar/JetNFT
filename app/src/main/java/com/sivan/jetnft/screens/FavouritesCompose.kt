package com.sivan.jetnft.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sivan.jetnft.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesCompose : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun FavouritesComposeRootView(mainViewModel: MainViewModel) {

    mainViewModel.getFavouritesList()
    val favouriteNFTs = mainViewModel.favouriteNFTList.value


        Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp)) {

            Text(text = "Favourites",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(12.dp))

            for (item in favouriteNFTs) {
                FavouriteItem(item)
                Divider()

            }

    }


}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview3() {
//    JetNFTTheme {
//        FavouritesComposeRootView(mainViewModel)
//    }
//}