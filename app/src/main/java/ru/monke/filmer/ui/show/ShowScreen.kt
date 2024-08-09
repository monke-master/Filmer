package ru.monke.filmer.ui.show

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.common.DescriptionItem
import ru.monke.filmer.ui.common.LoadingPlaceholder
import ru.monke.filmer.ui.common.RatingBadge
import ru.monke.filmer.ui.common.ShimmerPoster
import ru.monke.filmer.ui.common.getGenre
import ru.monke.filmer.ui.common.getRating
import ru.monke.filmer.ui.common.quantityStringResource
import ru.monke.filmer.ui.getMocked
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.SoftBlue

@Composable
fun ShowScreen(
    viewModel: ShowViewModel,
    showId: String,
    onBackButtonClicked: () -> Unit
) {
    LaunchedEffect(key1 = showId) {
        viewModel.getShow(showId)
    }

    val state by viewModel.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (state.isLoading) {
            LoadingPlaceholder(text = stringResource(id = R.string.loading_shows))
        } else if (state.error != null) {
            Text(text = state.error?.localizedMessage ?: " ")
        } else {
            state.show?.let { show ->
                ShowScreenContent(
                    show = show,
                    onBackButtonClicked = onBackButtonClicked
                )
            }
        }
    }

}

@Composable
private fun ShowScreenContent(
    show: Show,
    onBackButtonClicked: () -> Unit,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        ServicesDialog(
            services = show.services,
            onDismiss = {
                showDialog = false
            }
        )
    }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Box {
            BackgroundPoster(show)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Toolbar(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    title = show.title,
                    onBackButtonClicked = onBackButtonClicked
                )
                ShimmerPoster(
                    poster = show.posters.verticalPoster,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .height(287.dp)
                        .width(205.dp)
                        .clip(RoundedCornerShape(12.dp)))
                ShowDescription(
                    modifier = Modifier.padding(top = 16.dp),
                    show = show)
                RatingBadge(
                    rating = show.rating.getRating(),
                    modifier = Modifier.padding(top = 16.dp))
                PlayButton(
                    modifier = Modifier.padding(top = 24.dp),
                    onBtnClicked = {
                        showDialog = true
                    }
                )
            }
        }
        ShowOverview(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            overview = show.overview
        )
    }
}

@Composable
private fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    onBackButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .background(
                    color = SoftBlue,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(4.dp)
                .clickable { onBackButtonClicked() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null
        )
        Text(
            text = title
        )
        Icon(
            modifier = Modifier
                .background(
                    color = SoftBlue,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(4.dp),
            painter = painterResource(id = R.drawable.ic_like),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun BackgroundPoster(
    show: Show,
) {
    ShimmerPoster(
        poster = show.posters.verticalPoster,
        modifier = Modifier
            .fillMaxWidth()
            .height(520.dp)
            .alpha(0.2f)
            .drawWithCache {
                val startColor = Color(0x2E1F1D2B)
                val endColor = Color(0xFF1F1D2B)
                val gradient = Brush.verticalGradient(
                    colors = listOf(startColor, endColor),
                    startY = 0f,
                    endY = size.height
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            },
    )
}

@Composable
private fun ShowDescription(
    modifier: Modifier = Modifier,
    show: Show
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        DescriptionItem(
            icon = painterResource(id = R.drawable.ic_calendar),
            title = show.year.toString()
        )
        show.duration?.let {
            VerticalDivider(
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            DescriptionItem(
                icon = painterResource(id = R.drawable.ic_time),
                title = quantityStringResource(
                    id = R.plurals.minute,
                    quantity = show.duration, show.duration))
        }
        if (show.getGenre().isNotEmpty()) {
            VerticalDivider(
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            DescriptionItem(
                icon = painterResource(id = R.drawable.ic_movie),
                title = show.getGenre()
            )
        }
    }
}


@Composable
private fun PlayButton(
    modifier: Modifier = Modifier,
    onBtnClicked: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onBtnClicked,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = null)
        Text(text = stringResource(id = R.string.play))
    }
}

@Composable
private fun ShowOverview(
    modifier: Modifier = Modifier,
    overview: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.story_line),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = overview,
            style = MaterialTheme.typography.headlineMedium)
    }

}

@Preview
@Composable
fun ShowScreenPreview() {
    FilmerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            ShowScreenContent(
                show = getMocked(LocalContext.current.resources),
                onBackButtonClicked = {},
            )
        }
    }
}