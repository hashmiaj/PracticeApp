package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object LibraryRepository {
    private val _mockBooks = MutableStateFlow(emptyList<Book>())
    private val mockBooks : StateFlow<List<Book>> = _mockBooks.asStateFlow()

    suspend fun getBookById(id: String): Book? {
        delay(150) // pretend network/db latency
        return mockBooks.value.firstOrNull { it.id == id }
    }

    suspend fun getLibraryBooks(): List<Book> {
        delay(2000)
        val list = listOf(
            Book(
                id = "1",
                title = "To Kill a Mockingbird",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "2",
                title = "To Kill a Mockingbird 2",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "3",
                title = "To Kill a Mockingbird 3",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "4",
                title = "To Kill a Mockingbird 4",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "5",
                title = "To Kill a Mockingbird 5",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "6",
                title = "To Kill a Mockingbird 6",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "7",
                title = "To Kill a Mockingbird 7",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "8",
                title = "To Kill a Mockingbird 8",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
            Book(
                id = "9",
                title = "To Kill a Mockingbird 9",
                author = "Harper Lee",
                imageUrl = "https://images.unsplash.com/photo-1755545730104-3cb4545282b1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljJTIwbGl0ZXJhdHVyZXxlbnwxfHx8fDE3NTY2MzY3Nzd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                description = "A gripping tale of racial injustice and childhood innocence in the American South. Through the eyes of Scout Finch, we witness her father Atticus defend a black man falsely accused of rape, while learning profound lessons about morality, empathy, and standing up for what is right."
            ),
        )
        _mockBooks.value = list
        return list
    }
}