package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.app.Application
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(val app: Application): AndroidViewModel(app) {
    val listOfCharacters = MutableLiveData<List<CharacterModel>>()
    val repository: Repository = (app as App).api().repository
    lateinit var filePath: String

    fun getInitInfo(directoryPath: String, connectivityManager: ConnectivityManager) {
        repository.initCharacterList(directoryPath, listOfCharacters, connectivityManager)
    }
}