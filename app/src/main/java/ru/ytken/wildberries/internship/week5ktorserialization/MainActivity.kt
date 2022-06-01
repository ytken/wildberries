package ru.ytken.wildberries.internship.week5ktorserialization

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ActivityMainBinding
import ru.ytken.wildberries.internship.week5ktorserialization.entities.CatEntity
import ru.ytken.wildberries.internship.week5ktorserialization.entities.PostFavouritesRequest
import ru.ytken.wildberries.internship.week5ktorserialization.entities.PostResponse
import ru.ytken.wildberries.internship.week5ktorserialization.entities.PostVoteRequest

class MainActivity : AppCompatActivity() {
    companion object {
        val apiKey = "3b7fb1a4-d58d-477b-b118-0a59f8c2b8a8"

        val baseUrl = "https://api.thecatapi.com/v1"
        val getImageUrl = "$baseUrl/images/search"
        val postVoteUrl = "$baseUrl/votes"
        val getOrPostFavouritesUrl = "$baseUrl/favourites"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var ktorHttpClient: HttpClient
    private var currentCat = MutableLiveData<CatEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ktorHttpClient = getKtorClient()
        lifecycleScope.launch {
            currentCat.value = getRandomCat()[0]
        }

        currentCat.observe(this) {
            binding.draweeViewCat.setImageURI(it.url)
        }

        binding.imageButtonCross.setOnClickListener {
            lifecycleScope.launch {
                ktorHttpClient.post<PostResponse>(postVoteUrl) {
                    header("x-api-key", apiKey)
                    body = PostVoteRequest(
                        currentCat.value!!.id,
                        apiKey,
                        0
                    )
                }

                currentCat.value = getRandomCat()[0]
            }
        }

        binding.imageButtonHeart.setOnClickListener {
            lifecycleScope.launch {
                Toast.makeText(this@MainActivity, "Добавлен в избранные", Toast.LENGTH_SHORT).show()

                ktorHttpClient.post<PostResponse>(getOrPostFavouritesUrl) {
                    header("x-api-key", apiKey)
                    body = PostFavouritesRequest(
                        currentCat.value!!.id,
                        apiKey
                    )
                }

                ktorHttpClient.post<PostResponse>(postVoteUrl) {
                    header("x-api-key", apiKey)
                    body = PostVoteRequest(
                        currentCat.value!!.id,
                        apiKey,
                        1
                    )
                }

                currentCat.value = getRandomCat()[0]
            }
        }

        binding.imageButtonFavourites.setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
    }

    private suspend fun getRandomCat(): List<CatEntity> {
        val json = withContext(lifecycleScope.coroutineContext) {
            ktorHttpClient.get<String>(getImageUrl)
        }
        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString(json)
    }

    override fun onDestroy() {
        ktorHttpClient.close()
        super.onDestroy()
    }
}