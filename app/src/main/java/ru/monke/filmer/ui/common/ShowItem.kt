package ru.monke.filmer.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.Grey
import ru.monke.filmer.ui.theme.MontserratFamily
import ru.monke.filmer.ui.theme.Orange
import ru.monke.filmer.ui.theme.TransparentGrey

@Composable
fun RatingBadge(
    raring: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = TransparentGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 4.dp)
            ,
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
            tint = Orange,

            )
        Text(
            text = raring,
            color = Orange,
            fontFamily = MontserratFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingBadgePreview() {
    FilmerTheme {
        RatingBadge("6.4")
    }
}



@Composable
fun ShowItem(
    modifier: Modifier = Modifier,
    show: Show
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.today),
            style = MaterialTheme.typography.headlineLarge,
        )
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            ShowCover(
                cover = painterResource(id = R.drawable.ic_launcher_background),
                rating = show.rating.getRating()
            )
            ShowDescription(
                modifier = Modifier.padding(start = 16.dp),
                show = show
            )
        }
    }

}

@Composable
private fun ShowCover(
    cover: Painter,
    rating: String
) {
    Box() {
        Image(
            modifier = Modifier
                .width(112.dp)
                .height(147.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = cover,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        RatingBadge(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            raring = rating
        )
    }
}

@Composable
private fun ShowDescription(
    modifier: Modifier = Modifier,
    show: Show
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = show.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold
        )

        val itemsModifier = Modifier.padding(top = 12.dp)
        DescriptionItem(
            modifier = itemsModifier,
            icon = painterResource(id = R.drawable.ic_calendar),
            title = show.date.toString()
        )
        DescriptionItem(
            modifier = itemsModifier,
            icon = painterResource(id = R.drawable.ic_time),
            title = "${show.duration} ${quantityStringResource(R.plurals.minute, show.duration)}"
        )
        DescriptionItem(
            modifier = itemsModifier,
            icon = painterResource(id = R.drawable.ic_movie),
            title = show.category
        )
    }
}

@Composable
fun DescriptionItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = icon,
            contentDescription = null,
            tint = Grey
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = Grey
        )
    }
}