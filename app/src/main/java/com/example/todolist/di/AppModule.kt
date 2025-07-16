package com.example.todolist.di

import android.app.Application
import androidx.room.Room
import com.example.todolist.data.Todo
import com.example.todolist.data.TodoDatabase
import com.example.todolist.data.TodoRepository
import com.example.todolist.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase{
        return Room.databaseBuilder(
                app,
                TodoDatabase::class.java,
                "todo_database"
            ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: TodoDatabase): TodoRepository{
        return TodoRepositoryImpl(db.todoDao)
    }
}