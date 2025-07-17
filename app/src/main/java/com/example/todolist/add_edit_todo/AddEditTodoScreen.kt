package com.example.todolist.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.util.UIEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(onPopBackStack: () -> Unit, viewModel: AddEditViewModel = hiltViewModel()){
    val snackbarState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{event ->
            when(event){
                is UIEvent.PopBackStack -> {
                    onPopBackStack()
                }

                is UIEvent.ShowSnackBar -> {
                    snackbarState.snackbarHostState.showSnackbar(
                        event.message,
                        event.action
                    )
                }
                else ->{

                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(it)) {
            TextField(value = viewModel.title, onValueChange = {viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))}, label = { Text(text = "Title") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = viewModel.description, onValueChange = {viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))}, label = { Text(text = "Description") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = {viewModel.onEvent(AddEditTodoEvent.OnSaveTodo)}) {
                Text(text = "Save")
            }
        }
    }
}