package ru.ytken.wildberries.internship.week4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.ytken.wildberries.internship.week4.models.ChatData
import ru.ytken.wildberries.internship.week4.paging_sources.ChatsPagingSource
import ru.ytken.wildberries.internship.week4.paging_sources.MessagesPagingSource

class MainViewModel: ViewModel() {

    lateinit var repository: Repository
    lateinit var listChats: ArrayList<ChatData>

    init {
        repository = Repository()
    }

    fun getListData(): Flow<PagingData<ChatData>> {
        return Pager(config = PagingConfig(initialLoadSize = 20, pageSize = 10),
        pagingSourceFactory = { ChatsPagingSource(repository) }).flow.cachedIn(viewModelScope)
    }

    fun getChatDataByIndex(index: Int): ChatData = repository.getElementByIndex(index)

    fun getMessagesListData(index: Int): Flow<PagingData<Pair<Boolean, String>>> {
        return Pager(config = PagingConfig(initialLoadSize = 20, pageSize = 10),
            pagingSourceFactory = { MessagesPagingSource(repository, index) }).flow.cachedIn(viewModelScope)
    }

    fun changeListData() = repository.changeListData()

    fun updateMessages(indexOfChat: Int) = repository.updateMessages(indexOfChat)

}