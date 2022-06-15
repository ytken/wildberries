package ru.ytken.wildberries.internship.week5retrofitgson

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.ytken.wildberries.internship.week5retrofitgson.models.SuperheroEntity

interface ApiStorage {

    companion object {
        private const val apiKey = "3202191630032155"
        const val baseUrl = "https://akabab.github.io/superhero-api/api/"

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: ApiStorage by lazy {
            retrofit.create(ApiStorage::class.java)
        }
    }

    @GET("all.json")
    suspend fun getAllCharacters(): Response<List<SuperheroEntity>>
}