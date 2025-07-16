package com.example.todolist.todo_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.Todo
import com.example.todolist.data.TodoRepository
import com.example.todolist.data.TodoRepositoryImpl
import com.example.todolist.util.Routes
import com.example.todolist.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository): ViewModel(){
    val todos = repository.getAllTodos()

    private var deletedTodo: Todo? = null

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoEvent){
        when(event){
            is TodoEvent.OnAddTodo -> {
                sendUiEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is TodoEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UIEvent.ShowSnackBar(
                        "Todo has been deleted",
                        "Undo"))
                }
            }
            is TodoEvent.OnTodoClick -> {
                sendUiEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            TodoEvent.OnUndoTodoClick -> {
                viewModelScope.launch {
                    deletedTodo?.let { todo ->
                        repository.insertTodo(todo)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UIEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}