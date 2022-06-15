package ru.ytken.wildberries.internship.week5okhttpmoshi

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

{
    override fun toString(): String {
        var stringAttrs = "$id,$localizedName,$attackType,${roles?.joinToString(separator = " ")},$imgUrl,$iconUrl,$baseHealth,$baseMana,$baseAttackMin,$baseAttackMax"
        return stringAttrs
    }
}

fun String.parseCharacterModel(): CharacterModel? {
    val charAttributes = this.split(",")
    return if (charAttributes.size == 10)
        CharacterModel(
            charAttributes[0].toInt(),
            charAttributes[1],
            charAttributes[2],
            charAttributes[3].split(" "),
            charAttributes[4],
            charAttributes[5],
            charAttributes[6].toInt(),
            charAttributes[7].toInt(),
            charAttributes[8].toInt(),
            charAttributes[9].toInt()
        )
    else null
}