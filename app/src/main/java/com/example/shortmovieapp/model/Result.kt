package com.example.shortmovieapp.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
):Serializable
