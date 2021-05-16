package com.sivan.jetnft.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.sivan.jetnft.MainViewModel
import com.sivan.jetnft.database.model.NFTModel
import com.sivan.jetnft.database.model.NFTWithUserModel
import com.sivan.jetnft.database.model.UserModel
import com.sivan.jetnft.ui.theme.JetNFTTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

@AndroidEntryPoint
class PlaceABidActivity : ComponentActivity() {

    lateinit var nftModel: NFTWithUserModel

    val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nftModel = intent.getParcelableExtra<NFTWithUserModel>("nft_item")!!


        setContent {
            JetNFTTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    content = {
                        Box(modifier = Modifier
                            .padding(it)
                            .verticalScroll(rememberScrollState(), true)) {
                            PlaceABidRootView(nftModel, mainViewModel = mainViewModel)
                        }

                    },

                    bottomBar = {
                        ConfirmButton(mainViewModel, nftModel)
                    }
                )
            }
        }
    }
}

@Composable
fun ConfirmButton(mainViewModel: MainViewModel, nftModel: NFTWithUserModel) {
    val context = LocalContext.current

    var ethBid = mainViewModel.bidETH.observeAsState()

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value ) {

        if(ethBid.value == null) nftModel.nft.current_bid else ethBid.value?.let {
            ConfirmDialog(
                mainViewModel,
                nftModel = nftModel,
                ethValue = it,
                showDialog = showDialog
            ) {
                showDialog.value = false
            }
        }


    }


    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp)) {

        Card(elevation = 18.dp,
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
                .align(alignment = Alignment.Center)
            ,
            shape = CircleShape
        ) {
            Box(modifier = Modifier
                .background(color = Color.Black)
                .clickable {
                    if (ethBid.value != null && ethBid.value!! > nftModel.nft.current_bid) {
                        showDialog.value = true
                    } else {
                        showDialog.value = false
                    }

                }
            ) {
                Text(text = "Confirm",
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = Color.White)
            }


        }

    }}

@Composable
fun ConfirmDialog(
    mainViewModel: MainViewModel,
    nftModel: NFTWithUserModel,
    ethValue: Double,
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit) {


    if (showDialog.value) {

        val coroutineContext = rememberCoroutineScope()

        AlertDialog(
            onDismissRequest =  onDismiss,
            title = {
                Text(text = "Place a bid?")
            },
            text = {
                Text("Are you sure you want to place a bid for ${nftModel.nft.nftName} at ${ethValue} ETH ? ")
            },
            confirmButton = {
                Button(
                    modifier = Modifier.width(100.dp),
                    onClick = {
                        coroutineContext.launch(Dispatchers.IO) {
                            Log.d("Main", "ETH value : ${ethValue}")

                                placeBid(mainViewModel, nftModel, ethValue)

                        }

                        showDialog.value = false


                    }
                )
                {
                    Text("Yes")
                    // Send req to viewmodel to update database data


                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.width(100.dp),
                    onClick = { showDialog.value = false } )
                {
                    Text("No")
                }
            }
        )

    }
}



suspend fun placeBid(mainViewModel: MainViewModel, nftModel: NFTWithUserModel, ethValue: Double) {
    mainViewModel.placeBid(nftModel, ethValue)
}

@Composable
fun PlaceABidRootView(nftModel: NFTWithUserModel, mainViewModel: MainViewModel?) {
    Surface(modifier = Modifier.fillMaxSize()) {
       Column {

           Box(modifier = Modifier
               .fillMaxWidth()
               .padding(24.dp)) {
               BackButton()
               Text(text = "Place a bid", modifier = Modifier.align(alignment = Center),
                   style = MaterialTheme.typography.h6,
               fontWeight = FontWeight.Bold)
           }

           DetailsHolder(nftModel)

           ETHCounter(nftModel.nft.current_bid, mainViewModel)

           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
               Text(text = "You must bid at least ${nftModel.nft.current_bid.inc()} ETH \uD83D\uDD25", fontSize = 12.sp)
           }

           Spacer(modifier = Modifier.height(12.dp))
           Text(
               text = "HISTORY OF BID",
               modifier = Modifier.padding(24.dp),
               fontSize = 12.sp,
               fontWeight = FontWeight.Light
           )

           BidItem(mainViewModel, nftModel.nft.id)

       }
    }
}

@Composable
fun NFTCardSmall(nftImage: String) {
    Card(elevation = 18.dp, shape = RoundedCornerShape(12.dp)) {
        Image(painter = rememberCoilPainter(request = nftImage), contentDescription = "Nft image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp)))
    }
}

@Composable
fun DetailsHolder(nftModel: NFTWithUserModel) {
    Row(modifier = Modifier.padding(24.dp)) {
        NFTCardSmall(nftModel.nft.nftImage)

        Column(modifier = Modifier
            .height(120.dp)
            .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = nftModel.nft.nftName,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold)

            Text(text = nftModel.nft.nftDescription.split("\n", ignoreCase = true)[0],
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Light)

            // Get creator details from viewmodel
            Text(text = "Created by ${nftModel.user.name}",
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                color = Color.Blue)


        }

    }
}

@Preview(showBackground = true)
@Composable
fun ETHCounterView() {
    ETHCounter(1.0, null)
}

@Composable
fun ETHCounter(currentBid: Double, mainViewModel: MainViewModel?) {
    val value = 1.2
    val context = LocalContext.current

    val counterColorLight = Color(0xFADAE2E9)
    val counterColorDark = Color(0xFA272829)

    val counterCardBorder = Color(0xFACFDCE7)



    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(24.dp),
        border = BorderStroke(1.dp, counterCardBorder),
    shape = RoundedCornerShape(8.dp)) {
        

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                var ethValue by remember { mutableStateOf(currentBid) }
                var ethBid = mainViewModel?.bidETH?.observeAsState()


                Box(modifier = Modifier
                    .size(48.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = if (MaterialTheme.colors.isLight) counterColorLight else counterColorDark)
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        if (ethValue > currentBid)
                            ethValue--
                        updateETHValue(ethValue, mainViewModel)
                        Toast
                            .makeText(context, "Bid value : ${ethBid?.value}", Toast.LENGTH_LONG)
                            .show()


                    }
                ) {
                    Text(modifier = Modifier
                        .wrapContentSize()
                        .align(Center),text = "-", fontSize = 26.sp)
                }

                Row(modifier = Modifier
                    .wrapContentSize()
                    .align(alignment = CenterVertically)) {
                    ETHIcon()
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "${ethValue} ETH",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(alignment = CenterVertically))
                }


                Box(modifier = Modifier
                    .size(48.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = if (MaterialTheme.colors.isLight) counterColorLight else counterColorDark)
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        ethValue++
                        updateETHValue(ethValue, mainViewModel)
                    }
                ) {
                    Text(modifier = Modifier
                        .wrapContentSize()
                        .align(Center),text = "+", fontSize = 26.sp)
                }



        }
    }
}

fun updateETHValue(ethValue: Double, mainViewModel: MainViewModel?) {
    mainViewModel?.setBidETHValue(ethValue)
}


@Preview(showBackground = true)
@Composable
fun PlaceABidRootViewPreview() {
    val nftModel = NFTModel(
        id = 1,
        nftName = "Nyan Cat",
        creatorId = 1,
        current_bid = 1.2,
        nftDescription = "Nyan cat desctiption about an nft with a rainbow 8bit cat",
        nftImage = "https://raw.githubusercontent.com/sivansundar/JetNFT/master/app/src/main/res/drawable/nft_2.jpg",
        created_at = ZonedDateTime.now(),
        updated_at = ZonedDateTime.now()
    )

    val userModel = UserModel(
        id = 1,
        name = "Meta Kovan",
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
    PlaceABidRootView(nftWithUserModel, null)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    val nftModel = NFTModel(
        id = 1,
        nftName = "Nyan Cat",
        creatorId = 1,
        current_bid = 1.2,
        nftDescription = "Nyan cat desctiption about an nft with a rainbow 8bit cat",
        nftImage = "https://raw.githubusercontent.com/sivansundar/JetNFT/master/app/src/main/res/drawable/nft_2.jpg",
        created_at = ZonedDateTime.now(),
        updated_at = ZonedDateTime.now()
    )

    val userModel = UserModel(
        id = 1,
        name = "Meta Kovan",
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
    JetNFTTheme {
        PlaceABidRootView(nftWithUserModel, null)
    }
}