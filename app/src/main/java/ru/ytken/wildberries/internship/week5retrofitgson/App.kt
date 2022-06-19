package ru.ytken.wildberries.internship.week5retrofitgson

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application(){
    fun api(): ApiComponent = ApiModule
}

object ApiModule: ApiComponent {
    private const val apiKey = "3202191630032155"
    const val baseUrl = "https://akabab.github.io/superhero-api/api/"

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    override val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    override val api = retrofit.create(ApiStorage::class.java)
}