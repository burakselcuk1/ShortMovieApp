package com.example.shortmovieapp.model

import com.example.shortmovieapp.model.Result

data class Movie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
