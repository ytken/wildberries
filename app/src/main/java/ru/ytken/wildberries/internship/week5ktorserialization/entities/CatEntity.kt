package ru.ytken.wildberries.internship.week5ktorserialization.entities

import kotlinx.serialization.Serializable

@Serializable
data class CatEntity(
    val id: String,
    val url: String
    )