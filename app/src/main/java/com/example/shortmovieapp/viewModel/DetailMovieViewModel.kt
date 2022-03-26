package com.example.shortmovieapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shortmovieapp.model.Result
import com.example.shortmovieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val repository: MovieRepository):ViewModel() {

    val _movieDetail = MutableLiveData<Result>()


    fun getMovieDetail(id:String)=viewModelScope.launch {
        repository.getMovieDetail(id).let {response ->
            if (response.isSuccessful){
                _movieDetail.postValue(response.body())
            }
        }
    }

}