package com.sivan.jetnft.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class Composables {

    @Composable
    fun NFTCard() {
        Card(modifier = Modifier.fillMaxWidth().height(250.dp)
            .background(color = MaterialTheme.colors.surface)) {


        }
    }

    @Preview(showBackground = true)
    @Composable
    fun NFTCardPreview() {
        NFTCard()
    }
}