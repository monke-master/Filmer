package ru.monke.filmer.data.shows

interface PreferencesDataSource {

    fun getLastTodayShowRequest(): ShowRequest?

    fun setLastTodayShowRequest(showRequest: ShowRequest)
}