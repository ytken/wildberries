package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException

class MainViewModel(): ViewModel() {
    companion object {
        val url = "https://api.opendota.com"
        val baseUrl = "$url/api/heroStats"
    }

    var listOfCharacters = MutableLiveData<List<CharacterModel>>()
    private lateinit var moshiAdapter: JsonAdapter<List<CharacterModel>>
    private lateinit var fileName: String

    fun setFilePath(filePath: String) {
        fileName = filePath
        val fileInfo = getDataFromFile()
        listOfCharacters.postValue(fileInfo)
        if (fileInfo.isEmpty()) {
            initMoshi()
            initOkHttp()
        }
    }

    private fun getDataFromFile(): List<CharacterModel> {
        val file = File(fileName)
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

    fun writeDataToFile(characterList: List<CharacterModel>): Boolean {
        var stringData: String = ""
        for (char in characterList)
            stringData += "${char.toString()}\n"
        return try {
            File(fileName).writeText(stringData.toString())
            true
        } catch (e: IOException) {
            false
        }
    }

    private fun initMoshi() {
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, CharacterModel::class.java)
        moshiAdapter = moshi.adapter(type)
    }

    private fun initOkHttp() {
        val request = Request.Builder()
            .url(baseUrl)
            .build()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                listOfCharacters.postValue(emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                if (json != null) {
                    val listFromJson = moshiAdapter.fromJson(json)
                    listOfCharacters.postValue(listFromJson)
                    if (listFromJson != null)
                        writeDataToFile(listFromJson)
                }
            }
        })
    }
}