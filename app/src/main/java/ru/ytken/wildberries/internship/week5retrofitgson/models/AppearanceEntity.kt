package ru.ytken.wildberries.internship.week5retrofitgson.models

import java.io.Serializable

data class AppearanceEntity(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>,
    val eyeColor: String,
    val hairColor: String
): Serializable
{
    override fun toString(): String {
        return "$gender,$race,${height.joinToString(separator = " ")},${weight.joinToString(separator = " ")},$eyeColor,$hairColor"
    }
}