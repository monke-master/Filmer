package ru.monke.filmer.domain

data class Service(
    val id: String,
    val name: String,
    val serviceImages: ServiceImages,
    val link: String
)