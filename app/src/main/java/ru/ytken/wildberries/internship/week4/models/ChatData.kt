package ru.ytken.wildberries.internship.week4.models

import kotlin.random.Random

data class ChatData(
    val talkerName: String,
    val dialogList: ArrayList<Pair<Boolean, String>>,
    val imageUrl: String?,
    var numberOfNonViewedMessages: Int = 0
)