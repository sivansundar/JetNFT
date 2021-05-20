package com.sivan.jetnft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.sivan.jetnft.screens.*
import com.sivan.jetnft.ui.theme.JetNFTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    val mainViewModel = hiltViewModel<MainViewModel>()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Favourites,
        BottomNavigationScreens.Transactions,
        BottomNavigationScreens.Profile
    )
    val currentScreen = mainViewModel.currentScreen.value

    Scaffold(
        topBar = {
            if(currentScreen != "profile") {
                CustomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)

                )
            }

        },

        bottomBar = {
            NFTAppBottomNavigation(navController, bottomNavigationItems, mainViewModel)
        },
    ) {

            MainScreenNavigationConfigurations(navController, mainViewModel)

    }
}

@Composable
private fun NFTAppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>,
    mainViewModel: MainViewModel
) {
    BottomNavigation(
        modifier = Modifier
            .padding(36.dp)
            .clip(shape = RoundedCornerShape(26.dp)),

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "Bottom Nav Icon") },
                //label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false,// This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    mainViewModel.currentScreen.value = screen.route



                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationRoute.toString()) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },

            )
        }
    }
}


@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {


    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
       // val exampleViewModel = viewModel(HiltViewModelFactory(LocalContext.current, navController))<MainViewModel>()

        composable(BottomNavigationScreens.Home.route) {
            HomeRootView(mainViewModel)

            //Navigate(navController, BottomNavigationScreens.Home.route)
        }
        composable(BottomNavigationScreens.Favourites.route) {
            FavouritesComposeRootView(mainViewModel)
            //Navigate(navController, BottomNavigationScreens.Favourites.route)
        }
        composable(BottomNavigationScreens.Transactions.route) {
            TransactionsComposeRootView(mainViewModel)
            //Navigate(navController, BottomNavigationScreens.Transactions.route)
        }
        composable(BottomNavigationScreens.Profile.route) {
            ProfileComposeRootview(mainViewModel)
            //Navigate(navController, BottomNavigationScreens.Profile.route)
        }
    }
}

@Composable
fun Navigate(navController: NavHostController, route: String) {
    navController.navigate(route)
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    JetNFTTheme {
//        MainScreen(null)
//    }
//}