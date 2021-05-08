package com.sivan.jetnft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.sivan.jetnft.screens.Home
import com.sivan.jetnft.screens.HomeRootView
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
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "Bottom Nav Icon") },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false, // This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {

                        navController.navigate(screen.route)
                    }
                }
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
            Home("Fav")
            Navigate(navController, BottomNavigationScreens.Favourites.route)
        }
        composable(BottomNavigationScreens.Transactions.route) {
            Home("Transactions")
            Navigate(navController, BottomNavigationScreens.Transactions.route)
        }
        composable(BottomNavigationScreens.Profile.route) {
            Home("Profile")
            Navigate(navController, BottomNavigationScreens.Profile.route)
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