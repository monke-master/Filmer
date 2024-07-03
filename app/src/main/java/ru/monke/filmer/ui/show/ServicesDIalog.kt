package ru.monke.filmer.ui.show

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ru.monke.filmer.R
import ru.monke.filmer.domain.Service
import ru.monke.filmer.ui.common.IconButton
import ru.monke.filmer.ui.common.ShimmerPlaceholder
import ru.monke.filmer.ui.common.repeat
import ru.monke.filmer.ui.service
import ru.monke.filmer.ui.theme.DarkBlue
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.White

@Composable
fun ServicesDialog(
    services: List<Service>,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        ServicesDialogContent(services = services)
    }
}

@Composable
fun ServicesDialogContent(
    services: List<Service>
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                onClicked = { /*TODO*/ },
                painter = painterResource(id = R.drawable.ic_close),
                tint = Color.Unspecified,
                backgroundColor = DarkBlue,
                innerPadding = 8.dp)
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(id = R.string.watch),
                style = MaterialTheme.typography.headlineLarge,
                color = White
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = DarkBlue,
            )
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(services) {
                    ServiceItem(service = it)
                }
            }
        }

    }
}


@Composable
private fun ServiceItem(service: Service) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(service.link))
            startActivity(context, intent, null)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerServiceImage(service = service)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = service.name
        )
    }
}

@Composable
private fun ShimmerServiceImage(
    modifier: Modifier = Modifier,
    service: Service
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape),
        model = ImageRequest.Builder(LocalContext.current)
            .data(service.serviceImages.darkThemeImage)
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(true)
            .build(),
        loading = {
            ShimmerPlaceholder(modifier = modifier)
        },
        contentDescription = service.name,
        contentScale = ContentScale.Fit
    )
}

@Composable
@Preview(showBackground = true)
fun ServicesDialogPreview() {
    FilmerTheme {
        ServicesDialogContent(listOf(
            service
        ).repeat(3))
    }
}