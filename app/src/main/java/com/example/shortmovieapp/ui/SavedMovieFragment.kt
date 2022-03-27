package com.example.shortmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shortmovieapp.R
import com.example.shortmovieapp.adapter.SavedMovieAdapter
import com.example.shortmovieapp.databinding.FragmentDetailMovieBinding
import com.example.shortmovieapp.databinding.FragmentSavedMovieBinding
import com.example.shortmovieapp.model.Result
import com.example.shortmovieapp.viewModel.SavedMovieViewModel
import kotlinx.android.synthetic.main.fragment_saved_movie.*


class SavedMovieFragment : Fragment() {

    private var _binding : FragmentSavedMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var savedMovieViewModel:SavedMovieViewModel
    private lateinit var singleMovieData:Result
    private lateinit var roomAdapter:SavedMovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentSavedMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideViewModel()
        getMovie()
        readAllMovies()

        binding.swipeRefleshForSavedMovie.setOnRefreshListener {
            swipeRefleshSavedMovie()
        }
    }


    private fun provideViewModel() {
        savedMovieViewModel = ViewModelProvider(requireActivity()).get(SavedMovieViewModel::class.java)
    }

    private fun getMovie() {
        // Check if arguments null or not
        if(arguments?.get("movie")!= null) {
            if (requireArguments().getSerializable("movie") != null) {
                singleMovieData = requireArguments().getSerializable("movie") as Result

                val saveMovie = Result(
                    singleMovieData.adult,
                    singleMovieData.backdrop_path,
                    singleMovieData.id,
                    singleMovieData.original_language,
                    singleMovieData.original_title,
                    singleMovieData.overview,
                    singleMovieData.popularity,
                    singleMovieData.poster_path,
                    singleMovieData.release_date,
                    singleMovieData.original_title,
                    singleMovieData.video,
                    singleMovieData.vote_average,
                    singleMovieData.vote_count
                )
                savedMovieViewModel.addMovie(saveMovie)
            }
        }
    }

    private fun readAllMovies() {
        savedMovieViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            binding.savedMovieRecyclerview.layoutManager = LinearLayoutManager(context)
            roomAdapter = SavedMovieAdapter(it as ArrayList<Result>)
            binding.savedMovieRecyclerview.adapter = roomAdapter
        })
    }

    private fun swipeRefleshSavedMovie() {
        binding.savedMovieRecyclerview.visibility = View.GONE
        savedMovieViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            binding.savedMovieRecyclerview.layoutManager = LinearLayoutManager(context)
            roomAdapter = SavedMovieAdapter(it as ArrayList<Result>)
            binding.savedMovieRecyclerview.adapter = roomAdapter
            binding.savedMovieRecyclerview.visibility = View.VISIBLE
        })
        binding.swipeRefleshForSavedMovie.isRefreshing = false
        Toast.makeText(requireContext(),"Yenilendi!", Toast.LENGTH_SHORT).show()

    }
}