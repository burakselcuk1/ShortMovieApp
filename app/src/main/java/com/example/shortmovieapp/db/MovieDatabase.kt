package com.example.shortmovieapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shortmovieapp.model.Result


@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun toDoDao():MovieDao

}