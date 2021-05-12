package com.sivan.jetnft.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sivan.jetnft.R
import com.sivan.jetnft.database.model.NFTModel
import com.sivan.jetnft.screens.ui.theme.JetNFTTheme
import java.time.ZonedDateTime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle

class NFTActivity : ComponentActivity() {

    lateinit var nftModel: NFTModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nftModel = intent.getParcelableExtra<NFTModel>("nft_model")!!

        setContent {
            JetNFTTheme {
                // A surface container using the 'background' color from the theme
                NFTActivityRootView(nftModel)
            }
        }
    }
}

@Composable
fun NFTActivityRootView(nftModel: NFTModel) {
    Surface(color = MaterialTheme.colors.background) {
    Column() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)) {

            NFTImageCard(image = nftModel.nftImage, modifier = Modifier
                .fillMaxWidth()
                .height(400.dp))

            CreatorCardVariant(
                surfaceModifier = Modifier
                    .padding(12.dp, 24.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .align(alignment = Alignment.BottomStart)
                ,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )

            HeartButton(surfaceModifier = Modifier
                .wrapContentSize()
                .padding(24.dp, 24.dp)
                .clip(shape = CircleShape)
                .align(alignment = Alignment.BottomEnd))
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            NFTNameText(modifier = Modifier.padding(horizontal = 24.dp),
                name = nftModel.nftName)

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "DESCRIPTION", style = MaterialTheme.typography.subtitle2,
                    color = Color.Gray,
                    modifier = Modifier.padding(24.dp, 0.dp),
                fontWeight = FontWeight.Light)

            Spacer(modifier = Modifier.height(18.dp))

            Text(text = nftModel.nftDescription,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(24.dp, 0.dp),
                fontWeight = FontWeight.Bold)


        }
    }


    }
}


@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun HeartButton(surfaceModifier: Modifier) {

    var liked by remember { mutableStateOf(false) }

    var isLiked  = (if (liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder)
    Surface(modifier = surfaceModifier
    ) {
        Card(modifier = Modifier
            .size(48.dp)
            .background(Color.Transparent)
            .clickable { liked = !liked },
            shape = CircleShape) {



            Crossfade(targetState = liked) { liked->
                when(liked) {
                    true ->  RoundedIcon(Icons.Default.Favorite)

                    false -> RoundedIcon(Icons.Default.FavoriteBorder)

                }
            }


        }
    }

}

@Composable
fun RoundedIcon(imageVector: ImageVector) {
    Icon(
        imageVector =  imageVector, contentDescription = "Like button",
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White, CircleShape)
            .clip(CircleShape)                       // clip to the circle shape
        // add a border (optional)
    )
}

@Composable
fun CreatorCardVariant(surfaceModifier: Modifier, modifier: Modifier){
    
    Surface(modifier = surfaceModifier
    ) {
        Row(modifier = modifier) {
            ProfileButton(image_id = R.drawable.user_image,
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape))

            Spacer(modifier = Modifier.width(8.dp))

            Column(verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(36.dp)) {

                Text(text = "Hellyolaaa",
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold)


            }
        }
    }
    

}

@Preview(showBackground = true)
@Composable
fun NFTActivityRootViewPreview(){
    val nftModel = NFTModel(
        id = 1,
        nftName = "Nyan Cat",
        creatorId = 1,
        current_bid = 1.250,
        nftDescription = "Nyan cat desctiption about an nft with a rainbow 8bit cat",
        nftImage = "https://raw.githubusercontent.com/sivansundar/JetNFT/master/app/src/main/res/drawable/nft_2.jpg",
        created_at = ZonedDateTime.now(),
        updated_at = ZonedDateTime.now()
    )
    NFTActivityRootView(nftModel)
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