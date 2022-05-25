package ru.ytken.wildberries.internship.week4

enum class TypesOfChange(val index: Int) {
    NOTHING(0),
    NEW_CHAT(1),
    DELETED_CHAT(2),
    NEW_MESSAGE(3),
    DELETE_MESSAGE(4),
    CHANGED_MESSAGE_TEXT(5)
}