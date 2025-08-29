package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.BlogPost
import kotlinx.coroutines.delay

class BlogsRepository() {
    suspend fun fetchData(page: Int, pageSize: Int = 5): List<BlogPost> {
        delay(1500)
        val start = (page - 1) * pageSize + 1
        val end = start + pageSize - 1

        return (start..end).map { i ->
            BlogPost(
                i.toString(),
                "Blog $i",
                "This is blog number $i of this page!",
                "https://www.outbrain.com/blog/wp-content/uploads/2024/06/how-to-write-your-first-blog-post-2048x1152.png"
            )
        }
    }
}