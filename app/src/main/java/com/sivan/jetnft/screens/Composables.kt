package com.sivan.jetnft.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter
import com.sivan.jetnft.R
import com.sivan.jetnft.database.entity.NFTCacheEntity


@Composable
fun NFTList(list: List<NFTCacheEntity>?) {
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
    fun NFTCard(item: NFTCacheEntity) {
        val lightGrey = Color(0xFFFAFAFA)
        val lightBlack = Color(0xFF252525)
        val context = LocalContext.current

        //Toast.makeText(context, "List : ${item.nftName}", Toast.LENGTH_SHORT).show()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(shape = RoundedCornerShape(24.dp))
                ,
            elevation = 36.dp
        ) {

            Surface() {
                Column {
                    NFTImageCard(item.nftImage)
                    //Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = item.nftName,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp))

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                        CreatorCard()
                        PriceCard(item.current_bid)
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
fun CreatorCard(){
    Surface() {
        Row(modifier = Modifier
            .wrapContentSize()
            .padding(24.dp)) {
            ProfileButton(image_id = R.drawable.user_image,
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape))

            Spacer(modifier = Modifier.width(12.dp))

            Column(verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(36.dp)) {
                Text(text = "Creator",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Light)

                Text(text = "Hellyolaaa",
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
    CreatorCard()
}

@Composable
fun NFTImageCard(image : String) {

    Card(elevation = 24.dp,
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(24.dp)
        ) {
        Column() {
            Image(painter = rememberCoilPainter(request = image),
                contentDescription = "nft image",
                contentScale = ContentScale.Crop, modifier = Modifier
                    .fillMaxSize()
                    )
        }
    }


}



@Preview(showBackground = true)
@Composable
fun NFTImageCardPreview(){
    val sample = "https://drive.google.com/file/d/16A2WzHxYm616bQOpg0Fp0GztACXEoMPx/view?usp=sharing"
    NFTImageCard(sample)
}

@Preview(showBackground = true)
@Composable
    fun NFTCardPreview() {
        //NFTCard(null)
    }
