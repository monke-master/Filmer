package ru.monke.filmer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ru.monke.filmer.domain.Show

@Composable
fun ShimmerPoster(
    modifier: Modifier = Modifier,
    poster: String?
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(poster)
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(true)
            .build(),
        loading = {
            ShimmerPlaceholder(modifier = modifier)
        },
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}