package com.sivan.jetnft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.sivan.jetnft.screens.*
import com.sivan.jetnft.ui.theme.JetNFTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNFTTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Favourites,
        BottomNavigationScreens.Transactions,
        BottomNavigationScreens.Profile
    )
    Scaffold(
        bottomBar = {
            NFTAppBottomNavigation(navController, bottomNavigationItems)
        },
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}

@Composable
private fun NFTAppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation(
        modifier = Modifier.padding(36.dp).clip(shape = RoundedCornerShape(26.dp)),

    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "Bottom Nav Icon") },
                //label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false,// This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {

                        navController.navigate(screen.route)
                    }
                },

            )
        }
    }
}


@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeRootView()
            //Navigate(navController, BottomNavigationScreens.Home.route)
        }
        composable(BottomNavigationScreens.Favourites.route) {
            FavouritesComposeRootView()
            //Navigate(navController, BottomNavigationScreens.Favourites.route)
        }
        composable(BottomNavigationScreens.Transactions.route) {
            TransactionsComposeRootView()
            //Navigate(navController, BottomNavigationScreens.Transactions.route)
        }
        composable(BottomNavigationScreens.Profile.route) {
            ProfileComposeRootview()
            //Navigate(navController, BottomNavigationScreens.Profile.route)
        }
    }
}

@Composable
fun Navigate(navController: NavHostController, route: String) {
    navController.navigate(route)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetNFTTheme {
        MainScreen()
    }
}