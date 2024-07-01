package ru.monke.filmer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.monke.filmer.di.ApplicationComponent
import ru.monke.filmer.di.DaggerApplicationComponent
import ru.monke.filmer.di.daggerViewModel
import ru.monke.filmer.ui.home.HomeScreen
import ru.monke.filmer.ui.home.HomeViewModel
import ru.monke.filmer.ui.search.SearchScreen
import ru.monke.filmer.ui.search.SearchViewModel
import ru.monke.filmer.ui.show.ShowScreen
import ru.monke.filmer.ui.show.ShowViewModel
import ru.monke.filmer.ui.theme.FilmerTheme

@Composable
fun ScreenHolder(
    applicationComponent: ApplicationComponent
) {
    val navController = rememberNavController()
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Search
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Home.route) { entry ->
                val viewModel = daggerViewModel {
                    applicationComponent.homeViewModelFactory().create(HomeViewModel::class.java)
                }
                HomeScreen(
                    viewModel = viewModel,
                    onShowItemClicked = { show ->
                        navController.navigate("show/${show.id}")
                    }
                )
            }
            composable(NavigationItem.Search.route) {
                val viewModel = daggerViewModel {
                    applicationComponent.searchViewModelFactory().create(SearchViewModel::class.java)
                }
                SearchScreen(
                    searchViewModel = viewModel,
                    onShowItemClicked =  { show ->
                        navController.navigate("show/${show.id}")
                    }
                )
            }
            composable(
                route = "show/{showId}",
                arguments = listOf(navArgument("showId") { type = NavType.StringType })
            ) { backStackEntry ->
                val viewModel = daggerViewModel {
                    applicationComponent.showViewModelFactory().create(ShowViewModel::class.java)
                }
                ShowScreen(
                    viewModel = viewModel,
                    showId = backStackEntry.arguments?.getString(Params.SHOW_ID_PARAM)!!,
                    onBackButtonClicked = {
                        navController.popBackStack()
                    }
                )
            }

        }
    }
}

@Composable
@Preview
fun ScreenHolderPreview() {
    FilmerTheme {
        ScreenHolder(DaggerApplicationComponent.builder().build())
    }
}