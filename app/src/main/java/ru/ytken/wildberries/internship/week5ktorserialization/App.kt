package ru.ytken.wildberries.internship.week5ktorserialization

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import ru.ytken.wildberries.internship.week5ktorserialization.data.Repository
import ru.ytken.wildberries.internship.week5ktorserialization.data.database.FavouritesDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

    fun api(): ApiComponent = ApiModule(this)

    class ApiModule(app: App): ApiComponent {
        // Init ktor client
        val ktorClient  = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

        // Init Room DB
        val favouritesRoomDatabase = Room.databaseBuilder(
            app,
            FavouritesDatabase::class.java,
            "favourites_database"
        ).build()
        val favouritesDao = favouritesRoomDatabase.favouritesDao()

        // Init repo
        override val repository = Repository(favouritesDao, ktorClient)
    }
}

