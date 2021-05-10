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
import com.google.android.material.color.MaterialColors
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
    val LightWhite = Color(0xFFF2F2F7)
    val Black200 = Color(0xFF000000)

    Surface(modifier = Modifier.fillMaxSize(),
        color = if (MaterialTheme.colors.isLight) LightWhite else MaterialTheme.colors.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            CustomAppBar()
            Spacer(modifier = Modifier.height(24.dp))

            NFTCard()

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
            ProfileButton(R.drawable.user_image,
                Modifier
                    .wrapContentSize()
                    .clip(CircleShape))
        }

    }

}

@Composable
fun ProfileButton(image_id : Int, modifier: Modifier) {


    Card(modifier = Modifier.wrapContentSize(),
        elevation = 8.dp,
        shape = CircleShape) {

        Image(
            painter = painterResource(id = R.drawable.user_image),
            contentDescription = "Back button",
            contentScale = ContentScale.Crop,
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
fun PriceSectionPreview() {
    PriceSection(image_id = R.drawable.ethereum_icon)
}

@Composable
fun PriceSection(image_id : Int) {


    Card(modifier = Modifier.wrapContentSize(),

        elevation = 12.dp) {

        Image(
            painter = painterResource(id = image_id) ,
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