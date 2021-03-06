package com.sivan.jetnft.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.sivan.jetnft.MainViewModel
import com.sivan.jetnft.database.entity.BidCacheEntity
import com.sivan.jetnft.database.model.NFTWithUserModel
import com.sivan.jetnft.database.model.UserModel
import com.sivan.jetnft.ui.theme.JetNFTTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue


@AndroidEntryPoint
class NFTActivity : ComponentActivity() {

    lateinit var nftModel: NFTWithUserModel

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nftModel = intent.getParcelableExtra<NFTWithUserModel>("nft_model")!!

//        lifecycleScope.launch(Dispatchers.Main) {
//            mainViewModel.getLatestBid(nftModel.nft.id)
//        }

        setContent {
            JetNFTTheme {

                // A surface container using the 'background' color from the theme
                Scaffold(
                    content = {
                        Box(modifier = Modifier
                            .padding(it)
                            .verticalScroll(rememberScrollState(), true)) {
                            NFTActivityRootView(nftModel, mainViewModel)
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
fun NFTActivityRootView(nftModel: NFTWithUserModel, mainViewModel: MainViewModel) {
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



            mainViewModel.checkNFTFavourite(nftModel.nft.id)
            val isNFTFavourite = mainViewModel.isNFTFavourite.value
            var liked by remember { mutableStateOf(false) }
            liked = isNFTFavourite


            HeartButton(surfaceModifier = Modifier
                .wrapContentSize()
                .padding(24.dp, 24.dp)
                .clip(shape = CircleShape)
                .align(alignment = Alignment.BottomEnd),

                onClick = {
                    liked = !liked

                    if (liked) {
                        // Add to favourites
                        mainViewModel.addToFavourites(nftModel.nft.id)
                    } else {
                        // Remove from favourites
                        mainViewModel.removeFromFavourites(nftModel.nft.id)

                    }
            },
                    liked = liked)
        }

        Box() {
            Column(modifier = Modifier.fillMaxWidth()) {

                NFTNameText(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                    name = nftModel.nft.nftName)

                Spacer(modifier = Modifier.height(18.dp))

                Text(text = nftModel.nft.nftDescription,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(24.dp, 0.dp),
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "CURRENT BID", style = MaterialTheme.typography.subtitle2,
                    color = Color.Gray,
                    modifier = Modifier.padding(24.dp, 10.dp),
                    fontWeight = FontWeight.Light)


                // Get latest bid from database and pass it on to the composable.
                CurrentBid(mainViewModel, nftModel.nft.id)

            }
        }
    }
  }
}

@Composable
fun CurrentBid(mainViewModel: MainViewModel, id: Long) {
    mainViewModel.getLatestBidByNFT(id)
    val latestBid = mainViewModel.latestBidByNFT.value

    if (latestBid != null) {
        for (item in latestBid) {
            BidItem(latestBid = item)
        }
    } else {
        NoBidsYetText()
    }
    //SingleBidItem(latestBid)
}


@Composable
fun BidItem(latestBid: BidCacheEntity?) {

        if (latestBid !=null) {
            BidElement(latestBid)
        } else {
            NoBidsYetText()
        }

}


@Preview(showBackground = true)
@Composable
fun BidItemPreview(){
    BidItem(null)
}


@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun HeartButton(surfaceModifier: Modifier, onClick: () -> Unit, liked : Boolean) {

    //var liked by remember { mutableStateOf(false) }

    var isLiked  = (if (liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder)
    Surface(modifier = surfaceModifier
    ) {
        Card(modifier = Modifier
            .size(48.dp)
            .background(Color.Transparent)
            .clickable {
                // liked = !liked
                onClick()
            },
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
    //NFTActivityRootView(nftWithUserModel, null)
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