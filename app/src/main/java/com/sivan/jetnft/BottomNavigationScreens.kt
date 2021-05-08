package com.sivan.jetnft

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : BottomNavigationScreens("home", R.string.home, Icons.Rounded.Home)
    object Favourites : BottomNavigationScreens("favourites", R.string.favourites, Icons.Rounded.Favorite)
    object Transactions : BottomNavigationScreens("transactions", R.string.transactions, Icons.Rounded.Payments)
    object Profile : BottomNavigationScreens("profile", R.string.profile, Icons.Rounded.Person)



}
