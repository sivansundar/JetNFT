package com.sivan.jetnft.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sivan.jetnft.R
import com.sivan.jetnft.ui.theme.JetNFTTheme

class HomeCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNFTTheme {
                // A surface container using the 'background' color from the theme
                HomeRootView()

            }
        }
    }
}

@Composable
fun HomeRootView() {
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            CustomAppBar()
            Home("Android")
        }

    }
}

@Composable
fun CustomAppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),


    ) {
        Row(modifier = Modifier.align(alignment = Alignment.TopEnd)) {
            ProfileButton()
        }

    }

}

@Composable
fun ProfileButton() {


    Card(modifier = Modifier.wrapContentSize(),
        shape = CircleShape,
        elevation = 12.dp) {

        Image(
            painter = painterResource(id = R.drawable.user_image) ,
            contentScale = ContentScale.Crop,
            contentDescription = "Back button",
            modifier = Modifier
                .size(36.dp)
                .background(Color.White, CircleShape)
               // .border(border = BorderStroke(2.dp, Color.Gray), shape = CircleShape)// clip to the circle shape
            // add a border (optional)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun CustomAppBarPreview() {
    CustomAppBar()
}

@Composable
fun Home(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    HomeRootView()
}