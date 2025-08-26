package com.cftc.PracticeApp.network.data

import com.cftc.PracticeApp.model.BlogPost
import com.cftc.PracticeApp.network.api.BlogApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class BlogPostRepository(
    private val api: BlogApi,
    private val io: CoroutineDispatcher = Dispatchers.IO,
    private val pageSize: Int = 10,
    ) {
    private val _posts = MutableStateFlow<List<BlogPost>>(emptyList())
    val posts: StateFlow<List<BlogPost>> = _posts

    private var nextPage = 1
    private var endReached = false

    suspend fun loadInitialIfNeeded(): Result<Unit> = withContext(io) {
        if (_posts.value.isNotEmpty()) return@withContext Result.success(Unit)
        refresh()
    }

    suspend fun refresh(): Result<Unit> = withContext(io) {
        runCatching {
            val fresh = api.fetchPosts(page = 1, pageSize = pageSize)
            _posts.value = fresh
            nextPage = 2
            endReached = fresh.size < pageSize
        }
    }

    suspend fun loadMore(): Result<Unit> = withContext(io) {
        if (endReached) return@withContext Result.success(Unit)
        runCatching {
            val currentPage = nextPage
            val more = api.fetchPosts(page = currentPage, pageSize = pageSize)
            _posts.value = _posts.value + more
            nextPage = currentPage + 1
            if (more.size < pageSize) endReached = true
        }
    }

    fun canLoadMore(): Boolean = !endReached
}

/*

class BlogPostRepository(
    private val api: BlogApi,
    private val io: CoroutineDispatcher = Dispatchers.IO,
    private val pageSize: Int = 10
) {
    private val _posts = MutableStateFlow<List<BlogPost>>(emptyList())
    val posts: StateFlow<List<BlogPost>> = _posts

    private var nextPage = 1
    private var endReached = false

    suspend fun loadInitialIfNeeded(): Result<Unit> = withContext(io) {
        if (_posts.value.isNotEmpty()) return@withContext Result.success(Unit)
        refresh()
    }

    suspend fun refresh(): Result<Unit> = withContext(io) {
        runCatching {
            val fresh = api.fetchPosts(page = 1, pageSize = pageSize)
            _posts.value = fresh
            nextPage = 2
            endReached = fresh.size < pageSize
        }
    }

    suspend fun loadMore(): Result<Unit> = withContext(io) {
        if (endReached) return@withContext Result.success(Unit)
        runCatching {
            val currentPage = nextPage
            val more = api.fetchPosts(page = currentPage, pageSize = pageSize)
            _posts.value = _posts.value + more
            nextPage = currentPage + 1
            if (more.size < pageSize) endReached = true
        }
    }

    fun canLoadMore(): Boolean = !endReached
}
*/