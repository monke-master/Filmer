package ru.monke.filmer.ui.search.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.common.LoadingPlaceholder
import ru.monke.filmer.ui.common.SearchField
import ru.monke.filmer.ui.common.ShowItem
import ru.monke.filmer.ui.common.VerticalShowsList
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.Grey
import ru.monke.filmer.ui.theme.White

@Composable
fun SearchResultScreen(
    viewModel: SearchResultViewModel,
    onCancelBtnClicked: () -> Unit,
    onShowClicked: (Show) -> Unit
) {

    val state by viewModel.collectAsState()

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = focusRequester) {
        focusRequester.requestFocus()
    }

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .focusRequester(focusRequester),
                onTextChanged = viewModel::search
            )
            if (state.isLoading) {
                LoadingPlaceholder()
            } else if (state.isSuccessful) {
                if (state.result.isEmpty()) {
                    NotFoundPlaceholder()
                } else {
                    VerticalShowsList(
                        shows = state.result,
                        onLoadShow = {  },
                        onShowClicked = onShowClicked,
                        isLoading = state.isLoadingNext
                    )
                }
            } else if (state.error != null) {
                Text(text = "Чушь ебаная: " + state.error?.localizedMessage)
            }

        }
    }
}



@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchField(
            modifier = Modifier.weight(1f),
            value = text,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_hint),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            onValueChanged = {
                text = it
                onTextChanged(it)
            }
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .wrapContentWidth(),
            text = stringResource(id = R.string.cancel),
            maxLines = 1
        )
    }

}

@Composable
fun NotFoundPlaceholder(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_no_results),
            contentDescription = null,
            tint = Color.Unspecified)
        Text(
            modifier = Modifier.padding(start = 90.dp, end = 90.dp, top = 16.dp),
            text = stringResource(id = R.string.not_found),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.Center)
        Text(
            modifier = Modifier.padding(horizontal = 90.dp, vertical = 8.dp),
            text = stringResource(id = R.string.not_found_hint),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = Grey,
            textAlign = TextAlign.Center)
    }
}

@Composable()
@Preview(showBackground = true)
fun SearchResultPreview() {
    FilmerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // SearchResultScreen()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NotFoundPlaceholderPreview() {
    FilmerTheme {
        Surface {
            NotFoundPlaceholder(modifier = Modifier.fillMaxSize())
        }

    }
}