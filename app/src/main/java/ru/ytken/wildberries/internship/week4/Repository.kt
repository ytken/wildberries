package ru.ytken.wildberries.internship.week4

import android.util.Log
import io.github.serpro69.kfaker.faker
import kotlinx.coroutines.delay
import ru.ytken.wildberries.internship.week4.models.ChatData
import kotlin.random.Random

class Repository {
    companion object {
        private val MAX_NUMBER_OF_CHATS = 45
        private val INIT_NUMBER_OF_CHATS = 20
        private val NUMBER_OF_CHATS_PER_PAGE = 10
        private val faker = faker {  }
    }
    private var chatList: List<ChatData> = generateRandomChatList(INIT_NUMBER_OF_CHATS)

    private fun generateRandomChatList(size: Int): List<ChatData> =
        arrayListOf<ChatData>().apply {
            for (i in 0..size)
                this.add(generateRandomChat())
        }

    private fun generateRandomChat(): ChatData {
        return ChatData(
            "${faker.name.firstName()} ${faker.name.lastName()}",
            arrayListOf<Pair<Boolean, String>>().apply {
                for (i in 0..60)
                    this.add(Random.nextBoolean() to faker.naruto.characters())
            },
            null
        )
    }

    suspend fun getChats(pageNumber: Int): List<ChatData> {
        //Log.d(Repository::class.simpleName, "getChats pageNumber = $pageNumber")
        delay(2000)
        if(chatList.size < MAX_NUMBER_OF_CHATS)
            chatList = arrayListOf<ChatData>().apply {
                addAll(chatList)
                addAll(generateRandomChatList(NUMBER_OF_CHATS_PER_PAGE))
            }

        return when {
            pageNumber == 0 -> chatList
            (pageNumber +1 )* NUMBER_OF_CHATS_PER_PAGE > chatList.size ->
                chatList.subList(pageNumber*NUMBER_OF_CHATS_PER_PAGE, chatList.size-1)
            else ->
                chatList.subList(pageNumber*NUMBER_OF_CHATS_PER_PAGE, (pageNumber +1 )*NUMBER_OF_CHATS_PER_PAGE)
        }
    }

    suspend fun getMessages(index: Int, pageNumber: Int): ArrayList<Pair<Boolean, String>> {
        Log.d(Repository::class.simpleName, "Get messages for #$index Page#$pageNumber")
        val arrayListMessages = chatList[index].dialogList
        delay(1000)
        val size = arrayListMessages.size
        return arrayListMessages
    /*arrayListOf<Pair<Boolean, String>>().apply {
            addAll(arrayListMessages.subList(
                pageNumber* NUMBER_OF_CHATS_PER_PAGE,
                (pageNumber + 1)* NUMBER_OF_CHATS_PER_PAGE
            ))
        }*/
    /*when {
            pageNumber == 0 ->
                if (INIT_NUMBER_OF_CHATS >= size)
                    arrayListMessages.subList(size - 1 - INIT_NUMBER_OF_CHATS, size - 1) as ArrayList<Pair<Boolean, String>>
                else arrayListMessages
            INIT_NUMBER_OF_CHATS + pageNumber* NUMBER_OF_CHATS_PER_PAGE >= arrayListMessages.size ->
                arrayListMessages.subList(INIT_NUMBER_OF_CHATS + (pageNumber + 1)* NUMBER_OF_CHATS_PER_PAGE, size - 1) as ArrayList<Pair<Boolean, String>>
            else ->
                arrayListMessages.subList(
                    INIT_NUMBER_OF_CHATS + (pageNumber - 1)* NUMBER_OF_CHATS_PER_PAGE,
                    INIT_NUMBER_OF_CHATS + pageNumber* NUMBER_OF_CHATS_PER_PAGE) as ArrayList<Pair<Boolean, String>>
        }*/
    }

    private fun generateRandomString(length: Int): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ' '
        return (1..length)
            .map { charPool.random() }
            .joinToString("")
    }

    fun getElementByIndex(index: Int) = chatList[index]

    fun changeListData() {
        when (Random.nextInt(4)) {
            TypesOfChange.NEW_CHAT.index -> addNewChat()
            TypesOfChange.DELETED_CHAT.index -> deleteAnyChat()
            TypesOfChange.NEW_MESSAGE.index -> addNewMessageToChat()
        }
    }

    fun updateMessages(indexOfChat:Int) {
        when (Random.nextInt(3,6)) {
            TypesOfChange.NEW_MESSAGE.index -> addNewMessageToChatByIndex(indexOfChat)
            TypesOfChange.DELETE_MESSAGE.index -> deleteMessageFromChat(indexOfChat)
            TypesOfChange.CHANGED_MESSAGE_TEXT.index -> changeMessageTextInChat(indexOfChat)
        }
    }

    private fun addNewChat() {
        Log.d(Repository::class.simpleName, "Adding new chat")
        val newList = arrayListOf<ChatData>().apply {
            add(generateRandomChat())
            addAll(chatList)
        }
        chatList = newList
    }

    private fun deleteAnyChat() {
        val numberOfChat = Random.nextInt(8)
        Log.d(Repository::class.simpleName, "Deleting chat #$numberOfChat")
        val newList = arrayListOf<ChatData>().apply {
            addAll(chatList)
            removeAt(numberOfChat)
        }
        chatList = newList
    }

    private fun addNewMessageToChat() {
        val numberOfChat = Random.nextInt(8)
        val newMessage = generateRandomString(Random.nextInt(2, 50))
        Log.d(Repository::class.simpleName, "Adding to chat #$numberOfChat message: $newMessage")
        val elementAtNumber = chatList[numberOfChat]
        elementAtNumber.dialogList.add(false to newMessage)
        elementAtNumber.numberOfNonViewedMessages += 1
        chatList = arrayListOf<ChatData>().apply {
            addAll(chatList)
            this[numberOfChat] = elementAtNumber
        }
    }

    private fun addNewMessageToChatByIndex(index: Int) {
        val newMessage = generateRandomString(Random.nextInt(2, 50))
        val elementAtNumber = chatList[index]
        elementAtNumber.dialogList.add(false to newMessage)
        elementAtNumber.numberOfNonViewedMessages += 1
        chatList = arrayListOf<ChatData>().apply {
            addAll(chatList)
            this[index] = elementAtNumber
        }
    }

    private fun deleteMessageFromChat(indexOfChat: Int) {
        TODO("Not realized")
    }

    private fun changeMessageTextInChat(indexOfChat: Int) {
        TODO("Not realized")
    }
}