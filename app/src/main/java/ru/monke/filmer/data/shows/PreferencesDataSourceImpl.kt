package ru.monke.filmer.data.shows

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

private const val LAST_TODAY_SHOW_REQUEST_TIME = "LAST_TODAY_SHOW_REQUEST_TIME"
private const val LAST_TODAY_SHOW_ID = "LAST_TODAY_SHOW_ID"

class PreferencesDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferencesDataSource {

    
    override fun getLastTodayShowRequest(): ShowRequest? {
        val showId = sharedPreferences.getString(LAST_TODAY_SHOW_ID, null) ?: return null

        val time = sharedPreferences.getLong(LAST_TODAY_SHOW_REQUEST_TIME, -1)
        return ShowRequest(time, showId)
    }

    override fun setLastTodayShowRequest(showRequest: ShowRequest) {
        sharedPreferences.edit {
            putString(LAST_TODAY_SHOW_ID, showRequest.showId)
            putLong(LAST_TODAY_SHOW_REQUEST_TIME, showRequest.lastRequestTime)
            commit()
        }
    }
}