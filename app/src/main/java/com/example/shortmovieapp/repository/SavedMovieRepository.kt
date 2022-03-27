package com.example.shortmovieapp.repository

import androidx.lifecycle.LiveData
import com.example.shortmovieapp.db.MovieDao
import javax.inject.Inject
import com.example.shortmovieapp.model.Result

class SavedMovieRepository @Inject constructor(private val dao: MovieDao) {

    suspend fun insertToDo(toDo: Result)=dao.insertMovie(toDo)
    suspend fun deleteToDo(toDo: Result)=dao.deleteTodo(toDo)
    val readAllData: LiveData<List<Result>> = dao.getAllToDoes()
}