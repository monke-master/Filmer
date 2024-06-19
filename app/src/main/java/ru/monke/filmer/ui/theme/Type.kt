package ru.monke.filmer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.w3c.dom.Text
import ru.monke.filmer.R

val MontserratFamily = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_semi_bold, FontWeight.SemiBold)
)


// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 28.sp,
        letterSpacing = 0.12.sp
    ),
    displayMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 24.sp,
        letterSpacing = 0.12.sp
    ),
    displaySmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 18.sp,
        letterSpacing = 0.12.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 16.sp,
        letterSpacing = 0.12.sp,
        fontWeight = FontWeight.SemiBold
    ),
    headlineMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 14.sp,
        letterSpacing = 0.12.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 12.sp,
        letterSpacing = 0.12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 12.sp,
        letterSpacing = 0.12.sp
    )
)