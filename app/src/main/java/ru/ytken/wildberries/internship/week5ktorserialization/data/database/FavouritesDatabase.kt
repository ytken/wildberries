package ru.ytken.wildberries.internship.week5ktorserialization.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favourite::class], version = 1)
abstract class FavouritesDatabase: RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}