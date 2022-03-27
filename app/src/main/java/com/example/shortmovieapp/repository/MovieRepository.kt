package com.example.shortmovieapp.repository

import com.example.shortmovieapp.service.Api
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: Api) {

    suspend fun getMovies()=movieApi.getUpcomingList()
    suspend fun getNowPlayingList()=movieApi.getNowPlayingList()
    suspend fun getMovieDetail(id:String)=movieApi.getMovieDetail(id)
}