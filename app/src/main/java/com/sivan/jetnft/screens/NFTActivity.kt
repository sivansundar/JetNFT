package com.sivan.jetnft.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.sivan.jetnft.R
import com.sivan.jetnft.database.model.NFTModel
import java.time.ZonedDateTime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.sivan.jetnft.database.model.NFTWithUserModel
import com.sivan.jetnft.database.model.UserModel
import com.sivan.jetnft.ui.theme.JetNFTTheme

class NFTActivity : ComponentActivity() {

    lateinit var nftModel: NFTWithUserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nftModel = intent.getParcelableExtra<NFTWithUserModel>("nft_model")!!

        setContent {
            JetNFTTheme {

                // A surface container using the 'background' color from the theme
                Scaffold(
                    content = {
                        Box(modifier = Modifier
                            .padding(it)
                            .verticalScroll(rememberScrollState(), true)) {
                            NFTActivityRootView(nftModel)
                        }

                    },

                    bottomBar = {
                        PlaceABidButton(nftModel)
                    }
                )
            }
        }
    }
}

@Composable
fun PlaceABidButton(item: NFTWithUserModel?) {
    val context = LocalContext.current


    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp)) {
        Card(elevation = 18.dp,
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
                .align(alignment = Alignment.Center)
                ,
        shape = CircleShape) {
            Box(modifier = Modifier.background(color = Color.Black)
                .clickable {
                    context.startActivity(Intent(context, PlaceABidActivity::class.java)
                        .putExtra("nft_item", item))
                }) {
                    Text(text = "Place a bid",
                        modifier = Modifier.align(alignment = Alignment.Center),
                        color = Color.White)
                }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun BackButton() {

    val context = LocalContext.current

    Card(modifier = Modifier
        .size(42.dp)
        .background(Color.Transparent)
        .clickable { closeActivity(context) },
        shape = CircleShape,
        elevation = 18.dp) {
        Icon(
            imageVector =  Icons.Rounded.ArrowBack, contentDescription = "Back button",
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clip(CircleShape)                       // clip to the circle shape
            // add a border (optional)
        )



    }
}

fun closeActivity(context: Context) {
    (context as Activity).finish()
}

@Preview(showBackground = true)
@Composable
fun PlaceAButtonPreview() {
   PlaceABidButton(null)
}

@Composable
fun NFTActivityRootView(nftModel: NFTWithUserModel) {
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.wrapContentHeight()) {
    Column() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)) {



            NFTImageCard(image = nftModel.nft.nftImage, modifier = Modifier
                .fillMaxWidth()
                .height(400.dp))

            Column(modifier = Modifier.padding(12.dp, 20.dp)) {
                BackButton()
            }


            CreatorCardVariant(
                surfaceModifier = Modifier
                    .padding(12.dp, 24.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .align(alignment = Alignment.BottomStart)
                ,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp),
                user = nftModel.user
            )

            HeartButton(surfaceModifier = Modifier
                .wrapContentSize()
                .padding(24.dp, 24.dp)
                .clip(shape = CircleShape)
                .align(alignment = Alignment.BottomEnd))
        }

        Box() {
            Column(modifier = Modifier.fillMaxWidth()) {

                NFTNameText(modifier = Modifier.padding(horizontal = 24.dp),
                    name = nftModel.nft.nftName)


                Text(text = "DESCRIPTION", style = MaterialTheme.typography.subtitle2,
                    color = Color.Gray,
                    modifier = Modifier.padding(24.dp, 12.dp),
                    fontWeight = FontWeight.Light)

                Text(text = nftModel.nft.nftDescription,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(24.dp, 0.dp),
                    fontWeight = FontWeight.Bold)

                Text(text = "CURRENT BID", style = MaterialTheme.typography.subtitle2,
                    color = Color.Gray,
                    modifier = Modifier.padding(24.dp, 10.dp),
                    fontWeight = FontWeight.Light)


                // Get latest bid from database and pass it on to the composable.
                BidItem()


            }
        }




    }


    }
}

@Composable
fun BidItem(){
    val BidBGDark = Color(0xFA242222)
    val BidBGWhite = Color(0xFAF1ECEC)

    Surface(modifier = Modifier

        .padding(12.dp, 0.dp)
        .clip(shape = RoundedCornerShape(18.dp))


    ) {
        Row(modifier = Modifier
            .background(if (MaterialTheme.colors.isLight) BidBGWhite else BidBGDark)
            .fillMaxWidth()
            .padding(8.dp, 18.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
            ProfileButton(image_id = R.drawable.user_image)



            Column(verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(36.dp)) {

                Text(text = "Bid placed by Sivan",
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.SemiBold)

                Text(text = "April 25, 2021 at 10:30 AM",
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Light)


            }

            Text(text = "1.250 ETH",
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(alignment = Alignment.CenterVertically))

        }
    }


}

@Preview(showBackground = true)
@Composable
fun BidItemPreview(){
    BidItem()
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
            .clip(CircleShape)                       // clip to the circle shape
        // add a border (optional)
    )
}

@Composable
fun CreatorCardVariant(surfaceModifier: Modifier, modifier: Modifier, user: UserModel){
    
    Surface(modifier = surfaceModifier
    ) {
        Row(modifier = modifier) {
            ProfileButton(image_id = R.drawable.user_image)

            Spacer(modifier = Modifier.width(8.dp))

            Column(verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(36.dp)) {

                Text(text = user.name,
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

    val userModel = UserModel(
        id = 1,
        name = "Elon Musk",
        balance = 750,
        bio = "An early stage NFT lover | Digital Art | Doge to the Moon!",
        created_at = ZonedDateTime.now(),
        updated_at = ZonedDateTime.now(),
        publicKey = "0xasd50a498asfa2sa516a8s51da68sf41a3s21da6"
    )

    val nftWithUserModel = NFTWithUserModel(
        nft = nftModel,
        user = userModel
    )
    NFTActivityRootView(nftWithUserModel)
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