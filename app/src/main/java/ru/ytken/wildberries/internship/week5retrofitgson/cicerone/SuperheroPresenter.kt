package ru.ytken.wildberries.internship.week5retrofitgson.cicerone

import com.github.terrakok.cicerone.Router
import ru.ytken.wildberries.internship.week5retrofitgson.models.SuperheroEntity

class SuperheroPresenter(private val router: Router) {
    fun openHeroScreen(hero: SuperheroEntity) {
        router.navigateTo(Screens.HeroScreen(hero))
    }

    fun openAboutScreen() {
        router.navigateTo(Screens.AboutScreen())
    }

    fun onBackPressed() {
        router.exit()
    }
}