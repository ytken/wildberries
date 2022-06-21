package ru.ytken.wildberries.internship.week5retrofitgson

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

interface ApiComponent {
    // Retrofit
    val api: ApiStorage

    // Cicerone
    val router: Router
    val navigatorHolder: NavigatorHolder
}