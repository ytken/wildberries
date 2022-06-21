package ru.ytken.wildberries.internship.week5retrofitgson

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ytken.wildberries.internship.week5retrofitgson.cicerone.SuperheroPresenter
import ru.ytken.wildberries.internship.week5retrofitgson.models.SuperheroEntity
import ru.ytken.wildberries.internship.week5retrofitgson.models.parseSuperhero

class MainViewModel(val app: Application): AndroidViewModel(app) {
    val sharedPref = app.getSharedPreferences(app.resources.getString(R.string.superhero_file_key), Context.MODE_PRIVATE)
    val listOfSuperheroes = MutableLiveData<List<SuperheroEntity>>()

    val router = (app as App).api().router
    val navigatorHolder = (app as App).api().navigatorHolder
    val presenter = SuperheroPresenter(router)

    init {
        Log.d("MainViewModel", sharedPref.all.isEmpty().toString())
        if (sharedPref.all.isEmpty())
            getSuperheroesFromBackend()
        else
            getSuperheroesFromSharedPrefs()
    }

    private fun getSuperheroesFromBackend() {
        viewModelScope.launch {
            val responseSuperheroes = (app as App).api().api.getAllCharacters()
            if (responseSuperheroes.isSuccessful) {
                val responseList = responseSuperheroes.body()
                listOfSuperheroes.postValue(responseList)
                if (responseList != null && responseList.isNotEmpty())
                    for (hero in responseList)
                        putSuperheroToSharedPrefs(hero)
            }
        }
    }

    private fun getSuperheroesFromSharedPrefs() {
        val heroList = ArrayList<SuperheroEntity>()
        for (hero in sharedPref.all) {
            val heroString = hero.value as String
            val superhero = heroString.parseSuperhero()
            if (superhero != null)
                heroList.add(superhero)
        }
        listOfSuperheroes.postValue(heroList)
    }

    private fun putSuperheroToSharedPrefs(superheroEntity: SuperheroEntity) {
        with(sharedPref.edit()) {
            putString(superheroEntity.id.toString(), superheroEntity.toString())
            apply()
        }
    }

}