package ru.ytken.wildberries.internship.week5retrofitgson.models

import java.io.Serializable

data class ImagesEntity(
    val xs: String?,
    val sm: String?,
    val md: String?,
    val lg: String?
): Serializable {
    override fun toString(): String {
        return "$xs,$sm,$md,$lg"
    }
}