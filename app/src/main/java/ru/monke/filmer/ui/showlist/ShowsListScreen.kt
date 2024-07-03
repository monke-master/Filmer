package ru.monke.filmer.ui.showlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.common.IconButton
import ru.monke.filmer.ui.common.LoadingPlaceholder
import ru.monke.filmer.ui.common.VerticalShowsList
import ru.monke.filmer.ui.common.repeat
import ru.monke.filmer.ui.getMocked
import ru.monke.filmer.ui.theme.FilmerTheme

@Composable
fun ShowsListScreen(
    showsListViewModel: ShowsListViewModel,
    title: String,
    onShowItemClicked: (Show) -> Unit
) {
    LaunchedEffect(key1 = showsListViewModel) {
        showsListViewModel.fetchData()
    }

    val state by showsListViewModel.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (state.isLoading) {
            LoadingPlaceholder()
        } else if (state.error != null) {
            Text(text = "Ну ты пиздец")
        } else {
            ShowsListScreenContent(
                state = state,
                title = title,
                onItemClicked = onShowItemClicked,
                onItemsLoad = showsListViewModel::loadNext
            )
        }
    }
}

@Composable
private fun ShowsListScreenContent(
    state: ShowsListState,
    title: String,
    onItemClicked: (Show) -> Unit,
    onItemsLoad: () -> Unit,
) {
    Column {
        Toolbar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            title = title
        )
        VerticalShowsList(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 32.dp
            ),
            shows = state.items,
            onLoadShow = onItemsLoad,
            onShowClicked = onItemClicked,
            isLoading = state.isLoadingNext
        )
    }
}

@Composable
private fun Toolbar(
    modifier: Modifier = Modifier,
    title: String
) {
    Box(
        modifier = modifier,
    ) {
        IconButton(
            onClicked = {},
            painter = painterResource(id = R.drawable.ic_back),
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title
        )
    }
}

@Composable
@Preview
private fun ShowsListScreenPreview() {
    FilmerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            ShowsListScreenContent(
                title = "Летим в Хмеймим",
                state = ShowsListState(items = listOf(getMocked(LocalContext.current.resources)).repeat(2), isLoadingNext = true),
                onItemsLoad = {}, onItemClicked = {}
            )
        }

    }
}