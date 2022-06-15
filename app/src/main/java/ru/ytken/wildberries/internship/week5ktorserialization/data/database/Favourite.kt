package ru.ytken.wildberries.internship.week5ktorserialization.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.ytken.wildberries.internship.week5ktorserialization.entities.CatEntity

@Entity(tableName = "favourites_data")
data class Favourite(
    @PrimaryKey
    val id: String,
    val image: String
)