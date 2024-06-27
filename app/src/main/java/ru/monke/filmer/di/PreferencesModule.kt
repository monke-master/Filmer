package ru.monke.filmer.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

private const val SHOW_PREFERENCES = "show_preferences"

@Module
class PreferencesModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHOW_PREFERENCES, MODE_PRIVATE)
    }
}