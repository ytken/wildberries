package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(app: Application): AndroidViewModel(app) {
    val listOfCharacters = MutableLiveData<List<CharacterModel>>()
    val repository: Repository = (app as App).api().repository
    lateinit var filePath: String

    fun getInitInfo(directoryPath: String) {
        filePath = "${directoryPath}charactersInfo.txt"
        val fileInfo = repository.getDataFromFile(filePath)
        if (fileInfo.isEmpty()) {
            repository.initCharacterList(listOfCharacters)
        } else
            listOfCharacters.postValue(fileInfo)
    }
}