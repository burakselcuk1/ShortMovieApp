package com.example.shortmovieapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortmovieapp.repository.MovieRepository
import com.example.shortmovieapp.repository.SavedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.shortmovieapp.model.Result

@HiltViewModel
class SavedMovieDetailViewModel @Inject constructor(private val savedRepository: SavedMovieRepository,
                                                    private val movieRepository: MovieRepository):ViewModel() {

    val _movieDetail = MutableLiveData<Result>()

    fun getMovieDetailForSavedMovie(id:String)=viewModelScope.launch {
        movieRepository.getMovieDetail(id).let {response ->
            if (response.isSuccessful){
                _movieDetail.postValue(response.body())
            }
        }
    }

    fun deleteMovie(movie:Result) = viewModelScope.launch {
        savedRepository.deleteToDo(movie)
    }
}