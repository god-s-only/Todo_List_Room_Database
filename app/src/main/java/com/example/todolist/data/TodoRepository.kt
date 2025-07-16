package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    fun getAllTodos(): Flow<List<Todo>>
    fun getTodo(id: Int?): Todo?
}