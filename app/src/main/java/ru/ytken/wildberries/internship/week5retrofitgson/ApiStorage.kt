package ru.ytken.wildberries.internship.week5retrofitgson

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.ytken.wildberries.internship.week5retrofitgson.models.SuperheroEntity

interface ApiStorage {
    @GET("all.json")
    suspend fun getAllCharacters(): Response<List<SuperheroEntity>>
}