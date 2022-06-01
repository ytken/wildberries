package ru.ytken.wildberries.internship.week5ktorserialization.entities

import kotlinx.serialization.Serializable

@Serializable
data class PostVoteRequest(
    val image_id: String,
    val sub_id: String,
    val value: Int
)