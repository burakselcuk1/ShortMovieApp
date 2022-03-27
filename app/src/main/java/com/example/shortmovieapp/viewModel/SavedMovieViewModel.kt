package com.example.shortmovieapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortmovieapp.repository.MovieRepository
import com.example.shortmovieapp.repository.SavedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.shortmovieapp.model.Result

@HiltViewModel
class SavedMovieViewModel @Inject constructor(private val repository: SavedMovieRepository):ViewModel() {

    val readAllData: LiveData<List<Result>> = repository.readAllData

    fun addMovie(movie: Result)=viewModelScope.launch {
        repository.insertToDo(movie)
    }
}