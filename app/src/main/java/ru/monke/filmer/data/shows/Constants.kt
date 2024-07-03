package ru.monke.filmer.data.shows

const val BASE_URl = "https://streaming-availability.p.rapidapi.com/"
const val RAPID_API_KEY = "x-rapidapi-key"
const val API_KEY = "3ea3eb332amsh135d05e8b4a6a84p18e2b1jsn0660252f199f"

const val COUNTRY_PARAM = "country"
const val DEFAULT_COUNTRY_VALUE = "us"

const val SERVICE_PARAM = "service"
const val DEFAULT_SERVICE_VALUE = "netflix"

const val SHOWS_ENDPOINT = "shows"
const val TOP_SHOWS_ENDPOINT = "$SHOWS_ENDPOINT/top"
const val FILTERS_ENDPOINT = "shows/search/filters"

const val MIN_YEAR_PARAM = "year_min"

const val ID_PARAM = "id"
const val MIN_RATING_PARAM = "rating_min"
const val GOOD_RATING = 70

const val GENRES_ENDPOINT = "genres"

const val GENRES_PARAM = "genres"
const val CURSOR_PARAM = "cursor"

const val KEYWORD_PARAM = "keyword"

const val TITLE_ENDPOINT = "shows/search/title"
const val TITLE_PARAM = "title"