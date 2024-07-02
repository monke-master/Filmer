package ru.monke.filmer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.monke.filmer.R
import ru.monke.filmer.data.pagination.TopShowsLoader
import ru.monke.filmer.di.ApplicationComponent
import ru.monke.filmer.di.DaggerApplicationComponent
import ru.monke.filmer.di.daggerViewModel
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.home.HomeScreen
import ru.monke.filmer.ui.home.HomeViewModel
import ru.monke.filmer.ui.search.SearchScreen
import ru.monke.filmer.ui.search.SearchViewModel
import ru.monke.filmer.ui.show.ShowScreen
import ru.monke.filmer.ui.show.ShowViewModel
import ru.monke.filmer.ui.showlist.ShowsListScreen
import ru.monke.filmer.ui.showlist.ShowsListViewModel
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
            val onShowClicked: (Show) -> Unit = { navController.navigate("show/${it.id}") }
            composable(NavigationItem.Home.route) { entry ->
                val viewModel = daggerViewModel {
                    applicationComponent.homeViewModelFactory().create(HomeViewModel::class.java)
                }
                HomeScreen(
                    viewModel = viewModel,
                    onShowItemClicked = onShowClicked,
                    toShowsListNav = {
                        navController.navigate("showsList/bestShows")
                    }
                )
            }
            composable(NavigationItem.Search.route) {
                val viewModel = daggerViewModel {
                    applicationComponent.searchViewModelFactory().create(SearchViewModel::class.java)
                }
                SearchScreen(
                    searchViewModel = viewModel,
                    onShowItemClicked = onShowClicked,
                    toShowsListNav = { genre ->
                        navController.navigate("showsList/${genre.id}&${genre.name}")
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
            composable(
                route = "showsList/bestShows",
            ) { navBackStackEntry ->
                val viewModel = daggerViewModel {
                   applicationComponent.topShowsViewModelFactory().create(ShowsListViewModel::class.java)
                }
                ShowsListScreen(
                    showsListViewModel = viewModel,
                    title = stringResource(id = R.string.most_popular),
                    onShowItemClicked = onShowClicked
                )
            }
            composable(
                route = "showsList/{genreId}&{genreName}",
                arguments = listOf(
                    navArgument("genreId") { type = NavType.StringType },
                    navArgument("genreName") { type = NavType.StringType },
                    )
            ) { navBackStack ->
                val genreId = navBackStack.arguments?.getString("genreId")!!
                val genreName = navBackStack.arguments?.getString("genreName")!!
                val genre = Genre(genreId, genreName)
                val viewModel = daggerViewModel {
                    applicationComponent.recommendedShowViewModelFactory().create(ShowsListViewModel::class.java, genre)
                }
                ShowsListScreen(
                    showsListViewModel = viewModel,
                    title = stringResource(id = R.string.recommended_for_you),
                    onShowItemClicked = onShowClicked
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