package ru.ytken.wildberries.internship.week5ktorserialization.entities

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse (
    val message: String,
    val id: Int
        )