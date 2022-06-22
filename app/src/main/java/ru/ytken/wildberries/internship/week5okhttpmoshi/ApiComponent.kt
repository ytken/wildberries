package ru.ytken.wildberries.internship.week5okhttpmoshi

import com.squareup.moshi.JsonAdapter
import okhttp3.OkHttpClient

interface ApiComponent {
    val repository: Repository
}