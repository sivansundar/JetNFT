package com.sivan.jetnft.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.sivan.jetnft.MainViewModel
import com.sivan.jetnft.R
import com.sivan.jetnft.database.entity.BidCacheEntity
import com.sivan.jetnft.database.entity.NFTWithUserCacheEntity
import com.sivan.jetnft.database.entity.toNFTModel
import com.sivan.jetnft.database.entity.toUserModel
import com.sivan.jetnft.database.model.NFTModel
import com.sivan.jetnft.database.model.NFTWithUserModel
import com.sivan.jetnft.util.toDateTimeString


@Composable
fun NFTList(list: List<NFTWithUserCacheEntity>?) {
    if (list != null) {

        LazyColumn {
            items(list) {it->
                NFTCard(item = it )

                Spacer(modifier = Modifier.padding(24.dp))

            }

        }
    }

}

@Composable
fun NoBidsYetText() {
    Text(text = "No bids yet", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
}

@Composable
fun BidElement(item : BidCacheEntity) {
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

                Text(text = item.created_at.toDateTimeString(),
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Light)


            }

            Text(text = "${item.bidAmount} ETH",
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(alignment = Alignment.CenterVertically))

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
            Box(modifier = Modifier
                .background(color = Color.Black)
                .clickable {
                    context.startActivity(
                        Intent(context, PlaceABidActivity::class.java)
                            .putExtra("nft_item", item)
                    )
                }) {
                Text(text = "Place a bid",
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = Color.White)
            }


        }

    }

}

@Composable
fun BidItemList(mainViewModel: MainViewModel) {

    val bidList = mainViewModel.nftsByBid.value
    if (bidList!=null) {
        if (bidList.isNotEmpty()) {
            for (item in bidList) {
                Column(Modifier.fillMaxWidth()) {
                    BidElement(item )
                    Spacer(modifier = Modifier.height(12.dp))
                    //Divider()
                }
            }
        } else {
            NoBidsYetText()
        }
    }

    val context = LocalContext.current
    showToast(context, bidList)
    Log.d("Main", "Values ${bidList}")

}

fun showToast(context: Context, bidList: List<BidCacheEntity>?) {
    Toast.makeText(context, "Values ${bidList?.size}", Toast.LENGTH_LONG).show()
}

@Composable
    fun NFTCard(item: NFTWithUserCacheEntity) {
        val lightGrey = Color(0xFFFAFAFA)
        val lightBlack = Color(0xFF252525)
        val context = LocalContext.current

        //Toast.makeText(context, "List : ${item.nftName}", Toast.LENGTH_SHORT).show()

        val nftItemCopy = item.copy()

        val nftWithUser = NFTWithUserModel(
            nft = nftItemCopy.nft.toNFTModel(),
            user = nftItemCopy.user.toUserModel()
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(shape = RoundedCornerShape(24.dp))
                .clickable {
                    context.startActivity(
                        Intent(context, NFTActivity::class.java)
                            .putExtra("nft_model", nftWithUser)
                    )
                }
                ,
            elevation = 36.dp
        ) {

            Surface() {
                Column {
                    NFTImageCard(image = item.nft.nftImage,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(24.dp)
                    )
                    //Spacer(modifier = Modifier.height(18.dp))
                    NFTNameText(modifier = Modifier.padding(horizontal = 24.dp), name = item.nft.nftName)

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {

                        CreatorCard(modifier = Modifier
                            .wrapContentSize()
                            .padding(24.dp), nftItemCopy.user.name)
                        PriceCard(item.nft.current_bid)
                    }

                    val context = LocalContext.current

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (MaterialTheme.colors.isLight) lightGrey else lightBlack)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "Place a bid",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }) {
                        Text(modifier = Modifier.align(alignment = Alignment.Center),
                            text = "Place a Bid",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold
                            )
                    }


                }

            }

        }
    }

@Composable
fun NFTNameText(modifier: Modifier, name : String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = modifier)
}

@Composable
fun ETHIcon() {


    Card(modifier = Modifier.wrapContentSize(),
        elevation = 8.dp,
        shape = CircleShape) {

        Icon(
            painter = painterResource(id = R.drawable.ethereum_icon),
            contentDescription = "Back button",
            tint = Color.Blue,
            modifier = Modifier
                .size(36.dp)
                .background(Color.White, CircleShape)
                .padding(12.dp)
            // .border(border = BorderStroke(2.dp, Color.Gray), shape = CircleShape)// clip to the circle shape
            // add a border (optional)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun ETHIconPreview() {
    ETHIcon()
}


@Composable
fun CreatorCard(modifier: Modifier, name: String){
    Surface() {
        Row(modifier = modifier) {
            ProfileButton(image_id = R.drawable.user_image)

            Spacer(modifier = Modifier.width(12.dp))

            Column(verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(36.dp)) {
                Text(text = "Creator",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Light)

                Text(text = name,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold)


            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PriceCardPreview(){
    PriceCard(1.280)
}

@Composable
fun PriceCard(price : Double){
    Surface() {
        Row(modifier = Modifier
            .wrapContentSize()
            .padding(24.dp)) {
            ETHIcon()

            Spacer(modifier = Modifier.width(12.dp))

            Column(verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(36.dp)) {
                Text(text = "Current bid",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Light)

                Text(text = "${price} ETH",
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold)


            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CreatorCardPreview(){
    CreatorCard(
        modifier = Modifier
            .wrapContentSize()
            .padding(24.dp), name = "Sivan"
    )
}

@Composable
fun NFTImageCard(image : String, modifier: Modifier) {
    val painter = rememberCoilPainter(request = image)
    Card(elevation = 24.dp,
    modifier = modifier) {
        Column() {
            Image(painter = painter,
                contentDescription = "nft image",
                contentScale = ContentScale.Crop, modifier = Modifier
                    .fillMaxSize()
                    )

            when(painter.loadState) {
                ImageLoadState.Loading -> {
                    // Display a circular progress indicator whilst loading
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                }

                is ImageLoadState.Error -> {
                    // If
                // you wish to display some content if the request fails
                }
            }
        }
    }


}



@Preview(showBackground = true)
@Composable
fun NFTImageCardPreview(){
    val sample = "https://drive.google.com/file/d/16A2WzHxYm616bQOpg0Fp0GztACXEoMPx/view?usp=sharing"
    val modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(24.dp)

    NFTImageCard(sample, modifier)
}

@Preview(showBackground = true)
@Composable
    fun NFTCardPreview() {
        //NFTCard(null)
    }
