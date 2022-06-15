package ru.ytken.wildberries.internship.week5ktorserialization

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.Favourite
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.FavouritesDatabase
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.Repository
import ru.ytken.wildberries.internship.week5ktorserialization.entities.*

class MainViewModel(app: Application): AndroidViewModel(app) {
    companion object {
        val apiKey = "3b7fb1a4-d58d-477b-b118-0a59f8c2b8a8"
        val baseUrl = "https://api.thecatapi.com/v1"
        val getImageUrl = "$baseUrl/images/search"
        val postVoteUrl = "$baseUrl/votes"
        val getOrPostFavouritesUrl = "$baseUrl/favourites"
    }
    val ktorHttpClient: HttpClient = getKtorClient()
    val currentCat = MutableLiveData<CatEntity>()

    val listOfFavouritesBackend = MutableLiveData<List<GetFavouritesEntity>>()
    lateinit var listOfFavouritesRoom: LiveData<List<Favourite>>
    val listOfFavouriteCats = MutableLiveData<List<GetFavouritesEntity>>()
    private val repository: Repository

    init {
        viewModelScope.launch {
            currentCat.postValue(getRandomCat()[0])
        }

        val favouritesDao = FavouritesDatabase.getDatabase(app).favouritesDao()
        repository = Repository(favouritesDao)
    }

    suspend fun getRandomCat(): List<CatEntity> {
        val json = viewModelScope.async {
            ktorHttpClient.get<String>(getImageUrl)
        }.await()
        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString(json)
    }

    fun dislikeCurrentCat() {
        viewModelScope.launch {
            ktorHttpClient.post<PostResponse>(postVoteUrl) {
                header("x-api-key", apiKey)
                if (currentCat.value != null)
                    body = PostVoteRequest(
                        currentCat.value!!.id,
                        apiKey,
                        0
                    )
            }
            currentCat.value = getRandomCat()[0]
        }
    }

    fun likeCurrentCat() {
        viewModelScope.launch {
            ktorHttpClient.post<PostResponse>(getOrPostFavouritesUrl) {
                header("x-api-key", apiKey)
                body = PostFavouritesRequest(
                    currentCat.value!!.id,
                    apiKey
                )
            }
            currentCat.value = getRandomCat()[0]
        }
    }

    fun saveCurrentCatToFavourites() {
        viewModelScope.launch {
            ktorHttpClient.post<PostResponse>(postVoteUrl) {
                header("x-api-key", apiKey)
                body = PostVoteRequest(
                    currentCat.value!!.id,
                    apiKey,
                    1
                )
            }

            repository.addFavourite(Favourite(currentCat.value!!.id, currentCat.value!!.url))
        }
    }

    fun getListOfFavourites() {
        getListOfFavouritesFromDatabase()
    }

    private fun getListOfFavouritesFromDatabase() =
        viewModelScope.launch {
            listOfFavouritesRoom = repository.readAllData
        }

    fun getListOfFavouritesFromBackend() =
        viewModelScope.launch {
            val favourites = ktorHttpClient.get<List<GetFavouritesEntity>>(getOrPostFavouritesUrl) {
                header("x-api-key", apiKey)
            }
            listOfFavouritesBackend.postValue(favourites)
        }

    override fun onCleared() {
        ktorHttpClient.close()
        super.onCleared()
    }

}