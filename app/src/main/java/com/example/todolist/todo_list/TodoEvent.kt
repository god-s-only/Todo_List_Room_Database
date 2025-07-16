package com.example.todolist.todo_list

import com.example.todolist.data.Todo

sealed class TodoEvent {
    data object OnAddTodo: TodoEvent()
    data class OnDeleteTodo(val todo: Todo): TodoEvent()
    data class OnTodoClick(val todo: Todo): TodoEvent()
    data object OnUndoTodoClick: TodoEvent()
}