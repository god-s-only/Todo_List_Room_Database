package com.example.todolist.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.Todo
import com.example.todolist.data.TodoRepository
import com.example.todolist.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: TodoRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    private var todo by mutableStateOf<Todo?>(null)

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        if(todoId != -1){
            repository.getTodo(todoId)?.let { todo ->
                title = todo.title
                description = todo.description ?: ""
                this@AddEditViewModel.todo = todo
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent){
        when(event){
            is AddEditTodoEvent.OnSaveTodo -> {
                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiEvent(UIEvent.ShowSnackBar(
                            "Title cannot be blank",
                            null
                        ))
                        return@launch
                    }else{
                        repository.insertTodo(
                            Todo(
                                title = title,
                                description = description,
                                id = todo?.id
                            )
                        )
                        sendUiEvent(UIEvent.PopBackStack)
                    }
                }

            }

            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }
        }
    }

    private fun sendUiEvent(event: UIEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}