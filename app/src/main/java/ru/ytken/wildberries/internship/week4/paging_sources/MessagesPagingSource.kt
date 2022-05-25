package ru.ytken.wildberries.internship.week4.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.ytken.wildberries.internship.week4.Repository

class MessagesPagingSource(val repository: Repository, val index: Int): PagingSource<Int, Pair<Boolean, String>>() {
    override fun getRefreshKey(state: PagingState<Int, Pair<Boolean, String>>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<Boolean, String>> {
        return try {
            val nextPage = params.key ?: 0
            val response = repository.getMessages(index, nextPage)

            LoadResult.Page(data = response, prevKey = nextPage - 1, nextKey = nextPage + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}