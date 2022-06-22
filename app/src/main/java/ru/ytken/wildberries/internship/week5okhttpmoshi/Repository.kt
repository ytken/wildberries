package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonAdapter
import okhttp3.*
import java.io.File
import java.io.IOException

class Repository(
    val moshiAdapter: JsonAdapter<List<CharacterModel>>,
    val okHttpClient: OkHttpClient
) {
    companion object {
        val url = "https://api.opendota.com"
        val baseUrl = "${url}/api/heroStats"
    }
    lateinit var filePath: String

    fun initCharacterList(directoryPath: String, listOfCharacters: MutableLiveData<List<CharacterModel>>, connectivityManager: ConnectivityManager) {
        this.filePath = "${directoryPath}DotaCharactersInfo.txt"
        val fileInfo = getDataFromFile(filePath)
        if (fileInfo.isEmpty()) {
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                val request = Request.Builder()
                    .url(baseUrl)
                    .build()

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    okHttpClient.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            listOfCharacters.postValue(emptyList())
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val json = response.body?.string()
                            if (json != null) {
                                val listFromJson = moshiAdapter.fromJson(json)
                                listOfCharacters.postValue(listFromJson)
                                //if (listFromJson != null) writeDataToFile(filePath, listFromJson)
                            }
                        }
                    })
                }
            }

            connectivityManager.requestNetwork(networkRequest, networkCallback)
        }
    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private fun getDataFromFile(filePath: String): List<CharacterModel> {
        val file = File(filePath)
        return if (file.exists()) {
            val text = file.bufferedReader().readLines()
            val result = ArrayList<CharacterModel>()
            for (line in text) {
                val character = line.parseCharacterModel()
                if (character != null)
                    result.add(character)
            }
            result
        } else {
            file.createNewFile()
            emptyList()
        }
    }

    private fun writeDataToFile(filePath: String, characterList: List<CharacterModel>): Boolean {
        var stringData: String = ""
        for (char in characterList)
            stringData += "${char.toString()}\n"
        return try {
            File(filePath).writeText(stringData.toString())
            true
        } catch (e: IOException) {
            false
        }
    }
}