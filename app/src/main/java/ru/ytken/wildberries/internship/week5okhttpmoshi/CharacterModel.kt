package ru.ytken.wildberries.internship.week5okhttpmoshi

import androidx.versionedparcelable.VersionedParcelize
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CharacterModel(
    val id: Int,
    @Json(name = "localized_name") val localizedName: String,
    @Json(name = "attack_type") val attackType: String?,
    val roles: List<String>?,
    @Json(name = "img") val imgUrl: String,
    @Json(name = "icon") val iconUrl: String,
    @Json(name = "base_health") val baseHealth: Int?,
    @Json(name = "base_mana") val baseMana: Int?,
    @Json(name = "base_attack_min") val baseAttackMin: Int?,
    @Json(name = "base_attack_max") val baseAttackMax: Int?
): Serializable