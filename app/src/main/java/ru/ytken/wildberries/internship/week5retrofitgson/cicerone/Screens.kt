package ru.ytken.wildberries.internship.week5retrofitgson.cicerone

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ytken.wildberries.internship.week5retrofitgson.AboutFragment
import ru.ytken.wildberries.internship.week5retrofitgson.MainFragment
import ru.ytken.wildberries.internship.week5retrofitgson.SuperheroFragment
import ru.ytken.wildberries.internship.week5retrofitgson.models.SuperheroEntity

object Screens {

    fun MainScreen() = FragmentScreen { MainFragment() }

    fun HeroScreen(hero: SuperheroEntity) = FragmentScreen {
        val fragment = SuperheroFragment()
        fragment.arguments = Bundle().apply { putSerializable(MainFragment.tagSuperhero, hero) }
        fragment
    }

    fun AboutScreen() = FragmentScreen { AboutFragment() }
}