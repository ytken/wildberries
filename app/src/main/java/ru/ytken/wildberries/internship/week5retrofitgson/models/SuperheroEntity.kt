package ru.ytken.wildberries.internship.week5retrofitgson.models

import java.io.Serializable

data class SuperheroEntity(
    val id: Int,
    val name: String,
    val slug: String,
    val appearance: AppearanceEntity,
    val images: ImagesEntity
): Serializable
{
    override fun toString(): String {
        return "$id,$name,$slug,${appearance.toString()},${images.toString()}"
    }
}

fun String.parseSuperhero(): SuperheroEntity? {
    val heroStats = this.split(",")
    return if (heroStats.size == 13)
        SuperheroEntity(
            heroStats[0].toInt(),
            heroStats[1],
            heroStats[2],
            AppearanceEntity(
                heroStats[3],
                heroStats[4],
                heroStats[5].split(" "),
                heroStats[6].split(" "),
                heroStats[7],
                heroStats[8]
            ),
            ImagesEntity(
                heroStats[9],
                heroStats[10],
                heroStats[11],
                heroStats[12]
            )
        )
    else null
}