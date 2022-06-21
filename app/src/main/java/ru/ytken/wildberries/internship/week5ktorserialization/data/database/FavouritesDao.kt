package ru.ytken.wildberries.internship.week5ktorserialization.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.Favourite

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavourite(favourite: Favourite)

    @Query("SELECT * FROM favourites_data")
    suspend fun readAllFavourites(): List<Favourite>
}