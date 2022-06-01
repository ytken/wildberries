package ru.ytken.wildberries.internship.week5ktorserialization.entities

import kotlinx.serialization.Serializable
import ru.ytken.wildberries.internship.week5ktorserialization.entities.CatEntity

@Serializable
data class GetFavouritesEntity(
    val id: Int,
    val image: CatEntity
)