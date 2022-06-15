package ru.ytken.wildberries.internship.week5ktorserialization.data.database

import androidx.lifecycle.LiveData
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.Favourite
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.FavouritesDao

class Repository(private val favouritesDao: FavouritesDao) {

    val readAllData: LiveData<List<Favourite>> = favouritesDao.readAllFavourites()

    suspend fun addFavourite(favourite: Favourite) {
        favouritesDao.addFavourite(favourite)
    }

}