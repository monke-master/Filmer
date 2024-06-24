package ru.monke.filmer.ui

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import ru.monke.filmer.R
import ru.monke.filmer.domain.Posters
import ru.monke.filmer.domain.Show
import java.util.Calendar


val mockImageUrl = "https://sun6-22.userapi.com/impg/c853524/v853524338/1979c5/dplNqR4SoEg.jpg?size=720x570&quality=96&sign=a2cf2399cbbc347e7c7ec323c17de26c&c_uniq_tag=WVU_t-lMDHR_VpDJ__glkafICDW0FwbacrvD_4SoyFw&type=album"


//val mockedList = listOf(
//    Show("Зеленый Слоник", "Экшн", 12),
//    Show("Библиотекарь", "Экшн", 12),
//    Show("Красная жара", "Экшн", 12)
//)

fun getMocked(resources: Resources): Show {
    return Show(
        title = "Spider Man: Bomb Ukraine",
        category = "Action | SVO",
        rating = 98,
        duration = 123,
        year = Calendar.getInstance().timeInMillis.toInt(),
        posters = Posters(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(
            R.drawable.example_show) + '/' + resources.getResourceTypeName(R.drawable.example_show) + '/' + resources.getResourceEntryName(R.drawable.example_show) ).toString()),
        overview = "For the first time in the cinematic history of Spider-Man, our friendly neighborhood hero's identity is revealed, bringing his Super Hero responsibilities into conflict with his normal life and putting those he cares about most at risk. "
    )
}