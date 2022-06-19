package ru.ytken.wildberries.internship.week5retrofitgson

import okhttp3.OkHttpClient

interface ApiComponent {
    val okHttpClient: OkHttpClient
    val api: ApiStorage
}