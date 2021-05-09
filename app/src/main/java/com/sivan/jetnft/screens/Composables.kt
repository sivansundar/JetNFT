package com.sivan.jetnft.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sivan.jetnft.R



    @Composable
    fun NFTCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .clip(shape = RoundedCornerShape(24.dp)),
            elevation = 36.dp,
            contentColor = MaterialTheme.colors.onSurface
        ) {

            Surface() {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    NFTImageCard()
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "Nyan Cat.",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(18.dp))

                    CreatorCard()

                }

            }

        }
    }

@Composable
fun ETHIcon() {


    Card(modifier = Modifier.wrapContentSize(),

        elevation = 12.dp) {

        Icon(
            painter = painterResource(id = R.drawable.ic_eth_icon),
            contentDescription = "Back button",
            tint = Color.Blue,
            modifier = Modifier
                .size(24.dp)
                .background(Color.White, CircleShape)
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
        Row(modifier = Modifier.wrapContentSize()) {
            ProfileButton(image_id = R.drawable.user_image,
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape))

            Spacer(modifier = Modifier.width(12.dp))

            Column(verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(36.dp)) {
                Text(text = "Creator",
                    fontSize = 8.sp)

                Text(text = "Hellyolaaa",
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
fun NFTImageCard() {

    Card(elevation = 24.dp,
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        ) {
        Column() {
            Image(painter = painterResource(id = R.drawable.nft_2), contentDescription = "nft image",
                contentScale = ContentScale.Crop, modifier = Modifier
                    .fillMaxSize()
                    )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun NFTImageCardPreview(){
    NFTImageCard()
}

@Preview(showBackground = true)
@Composable
    fun NFTCardPreview() {
        NFTCard()
    }
