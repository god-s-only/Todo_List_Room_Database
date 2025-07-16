package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.todo_list.TodoListScreen
import com.example.todolist.ui.theme.TodoListTheme
import com.example.todolist.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST){
                    composable(Routes.TODO_LIST) {
                        TodoListScreen(onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                }
            }
        }
    }
}



