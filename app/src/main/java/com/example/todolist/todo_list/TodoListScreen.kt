package com.example.todolist.todo_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.data.Todo
import com.example.todolist.util.UIEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
){
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val snackbarState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UIEvent.Navigate -> {
                    onNavigate(event)
                }
                is UIEvent.ShowSnackBar -> {
                    val result = snackbarState.snackbarHostState.showSnackbar(
                        event.message,
                        event.action
                    )
                    if(result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(TodoEvent.OnUndoTodoClick)
                    }
                }
                else -> {

                }
            }
        }

    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TodoEvent.OnAddTodo)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(it)) {
            LazyColumn {
                item {
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        Text(text = "Todo(s)", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
                items(todos.value){todoItem ->
                    TodoItem(todoItem)
                }
            }
        }
    }

}
@Composable
fun TodoItem(todoItem: Todo, viewModel: TodoViewModel = hiltViewModel()){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp),
            border = BorderStroke(1.dp, color = Color.Black),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = todoItem.title, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = todoItem.description ?: "", maxLines = 5, overflow = TextOverflow.Ellipsis, fontSize = 20.sp)
                }
                IconButton(onClick = {viewModel.onEvent(TodoEvent.OnDeleteTodo(todoItem))}) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
}