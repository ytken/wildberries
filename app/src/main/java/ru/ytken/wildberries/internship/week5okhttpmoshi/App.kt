package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

class App: Application() {
    fun api(): ApiComponent = ApiModule(filesDir.absolutePath)

    class ApiModule(filePath: String): ApiComponent {
        private val moshi: Moshi = Moshi.Builder().build()
        private val type = Types.newParameterizedType(List::class.java, CharacterModel::class.java)
        private val moshiAdapter: JsonAdapter<List<CharacterModel>> = moshi.adapter(type)

        private val logging = HttpLoggingInterceptor()
        private val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        override val repository = Repository(moshiAdapter, okHttpClient)
    }
}