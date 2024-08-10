package ru.monke.filmer.ui.showlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val state by showsListViewModel.collectAsState()

    ShowsListScreenContent(
        state = state,
        title = title,
        onShowItemClicked = onShowItemClicked ,
        onLoadNext = showsListViewModel::loadNext
    )
}

@Composable
private fun ShowsListScreenContent(
    state: ShowsListState,
    title: String,
    onShowItemClicked: (Show) -> Unit,
    onLoadNext: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Toolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                title = title
            )
            when (state) {
                is ShowsListState.Error -> Text(text = "Ну ты пиздец${state.error}")
                ShowsListState.Idle -> {}
                ShowsListState.Loading -> LoadingPlaceholder(modifier = Modifier.fillMaxSize())
                is ShowsListState.Success -> SuccessState(
                    state = state,
                    onItemClicked = onShowItemClicked,
                    onItemsLoad = onLoadNext
                )
            }
        }

    }
}

@Composable
private fun SuccessState(
    state: ShowsListState.Success,
    onItemClicked: (Show) -> Unit,
    onItemsLoad: () -> Unit,
) {
    VerticalShowsList(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 16.dp
            )
            .fillMaxHeight(),
        shows = state.items,
        onLoadShow = onItemsLoad,
        onShowClicked = onItemClicked,
        isLoading = state.isLoadingNext
    )
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
private fun SuccessStatePreview() {
    FilmerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            ShowsListScreenContent(
                title = "Летим в Хмеймим",
                state = ShowsListState.Success(items = listOf(getMocked(LocalContext.current.resources)).repeat(2), isLoadingNext = true),
                onLoadNext = {}, onShowItemClicked = {}
            )
        }
    }
}

@Composable
@Preview
private fun LoadingStatePreview() {
    FilmerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            ShowsListScreenContent(
                title = "Летим в Хмеймим",
                state = ShowsListState.Loading,
                onLoadNext = {}, onShowItemClicked = {}
            )
        }
    }
}

@Composable
@Preview
private fun ErrorStatePreview() {
    FilmerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            ShowsListScreenContent(
                title = "Летим в Хмеймим",
                state = ShowsListState.Error(StackOverflowError()),
                onLoadNext = {}, onShowItemClicked = {}
            )
        }
    }
}