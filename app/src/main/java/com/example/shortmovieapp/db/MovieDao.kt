package com.example.shortmovieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shortmovieapp.model.Result

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Result)

    @Delete
    suspend fun deleteTodo(movie: Result)

    @Query("SELECT * FROM movie_data ORDER BY id ASC ")
    fun getAllToDoes(): LiveData<List<Result>>

}