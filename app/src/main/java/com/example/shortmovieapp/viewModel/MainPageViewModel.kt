package com.example.shortmovieapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortmovieapp.model.Movie
import com.example.shortmovieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(private val repository: MovieRepository):ViewModel() {

    val _movie = MutableLiveData<Movie>()
    val _nowPlaying = MutableLiveData<Movie>()

    init {
        getMovies()
        getNowPlaying()
    }

    private fun getNowPlaying()=viewModelScope.launch {
        repository.getNowPlayingList().let { responseNowPlaying ->
            if (responseNowPlaying.isSuccessful){
                _nowPlaying.postValue(responseNowPlaying.body())
            }else{
                Log.e("Brk:MainPageViewModel","error Now Playing")
            }
        }
    }

    fun getMovies()=viewModelScope.launch {
        repository.getMovies().let {response ->
            if (response.isSuccessful){
                _movie.postValue(response.body())
            }else{
                Log.e("Brk:MainPageViewModel","error")
            }
        }
    }
}