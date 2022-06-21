package ru.ytken.wildberries.internship.week5ktorserialization.data

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.Favourite
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.FavouritesDao
import ru.ytken.wildberries.internship.week5ktorserialization.entities.*

class Repository(private val favouritesDao: FavouritesDao, private val ktorHttpClient: HttpClient) {
    companion object {
        val apiKey = "3b7fb1a4-d58d-477b-b118-0a59f8c2b8a8"
        val apiKeyLabel = "x-api-key"
        val baseUrl = "https://api.thecatapi.com/v1"
        val getImageUrl = "$baseUrl/images/search"
        val postVoteUrl = "$baseUrl/votes"
        val getOrPostFavouritesUrl = "$baseUrl/favourites"
    }

    suspend fun readAllFavourites(): List<GetFavouritesEntity> {
        val listOfFavouritesRoom = favouritesDao.readAllFavourites()
        return if (listOfFavouritesRoom.isEmpty()) {
            val favourites = ktorHttpClient.get<List<GetFavouritesEntity>>(getOrPostFavouritesUrl) {
                header("x-api-key", apiKey)
            }
            favourites
        } else
            listOfFavouritesRoom.map {
                GetFavouritesEntity(0, CatEntity(it.id, it.image))
            }
    }

    suspend fun addFavourite(currentCat: CatEntity) {
        ktorHttpClient.post<PostResponse>(postVoteUrl) {
            header("x-api-key", apiKey)
            body = PostVoteRequest(
                currentCat.id,
                apiKey,
                1
            )
        }

        favouritesDao.addFavourite(Favourite(currentCat.id, currentCat.url))
    }

    suspend fun getNewCat(): List<CatEntity> {
        val json = ktorHttpClient.get<String>(getImageUrl)
        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString(json)
    }

    suspend fun likeCat(currentCat: CatEntity) {
        ktorHttpClient.post<PostResponse>(getOrPostFavouritesUrl) {
            header("x-api-key", apiKey)
            body = PostFavouritesRequest(
                currentCat.id,
                apiKey
            )
        }
    }

    suspend fun dislikeCat(currentCat: CatEntity) {
        ktorHttpClient.post<PostResponse>(postVoteUrl) {
            header(apiKeyLabel, apiKey)
            body = PostVoteRequest(
                currentCat.id,
                apiKey,
                0
            )
        }
    }

}