package ru.ytken.wildberries.internship.week4.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.ytken.wildberries.internship.week4.Repository
import ru.ytken.wildberries.internship.week4.models.ChatData

class ChatsPagingSource(val repository: Repository): PagingSource<Int, ChatData>() {
    override fun getRefreshKey(state: PagingState<Int, ChatData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatData> {
        return try {
            val nextPage = params.key ?: 0
            val response = repository.getChats(nextPage)

            LoadResult.Page(data = response, prevKey = nextPage - 1, nextKey = nextPage + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}