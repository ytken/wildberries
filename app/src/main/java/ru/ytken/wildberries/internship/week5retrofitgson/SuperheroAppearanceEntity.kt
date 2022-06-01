package ru.ytken.wildberries.internship.week5retrofitgson

import com.google.gson.annotations.SerializedName

data class SuperheroAppearanceEntity(
    val response: String,
    val id: String,
    val name: String,
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>,
    @SerializedName("eye-color")
    val eyeColor: String,
    @SerializedName("hair-color")
    val hairColor: String
)