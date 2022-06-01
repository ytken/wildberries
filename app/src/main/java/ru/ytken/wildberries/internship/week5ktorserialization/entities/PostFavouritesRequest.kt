package ru.ytken.wildberries.internship.week5ktorserialization.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostFavouritesRequest (
    val image_id: String,
    val sub_id: String
    )