package com.example.todolist.add_edit_todo

sealed class AddEditTodoEvent {
    data object OnSaveTodo: AddEditTodoEvent()
    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
}