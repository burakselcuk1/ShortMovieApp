package com.example.shortmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.shortmovieapp.R
import com.example.shortmovieapp.Util.Constans.Companion.POSTER_MAIN_URL
import com.example.shortmovieapp.databinding.FragmentDetailMovieBinding
import com.example.shortmovieapp.databinding.FragmentSavedMovieDetailBinding
import com.example.shortmovieapp.model.Result
import com.example.shortmovieapp.viewModel.SavedMovieDetailViewModel
import kotlinx.android.synthetic.main.fragment_saved_movie_detail.*

class SavedMovieDetailFragment : Fragment() {

    private var _binding : FragmentSavedMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel:SavedMovieDetailViewModel
    lateinit var resultMovie: Result

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentSavedMovieDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideViewModel()

        val args = this.arguments
        val movieId: String? = args?.getString("movieIdd","databoss")

        viewModel.getMovieDetailForSavedMovie(movieId.toString())

        placeDataToSavedMovieDetailFragment()

        binding.deleteMovieToRoomDb.setOnClickListener {
            deleteMovieFromRoomDb()
        }

    }

    private fun deleteMovieFromRoomDb() {
        viewModel._movieDetail.observe(viewLifecycleOwner, Observer {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Evet"){_, _ ->
                viewModel.deleteMovie(it)
                findNavController().navigate(R.id.action_savedMovieDetailFragment_to_mainPageFragment)
                Toast.makeText(requireContext(),"Film Silindi!", Toast.LENGTH_SHORT).show()


            }
            builder.setNegativeButton("Hayır"){_, _ ->}
            builder.setTitle("Sil - ${it.original_title}")
            builder.setMessage("Bu filmi silmek istediğinizden emin misiniz ${it.original_title} ?")
            builder.create().show()

        })
    }

    private fun placeDataToSavedMovieDetailFragment() {
        viewModel._movieDetail.observe(this, Observer {
            resultMovie = it
            binding.savedMovieDetailName.text = resultMovie.original_title
            binding.savedMovieDetailAvarage.text = resultMovie.vote_average.toString()+"/10"
            binding.savedMovieDetailRealizeDate.text = resultMovie.release_date
            val url= POSTER_MAIN_URL + resultMovie.poster_path
            Glide.with(this).load(url).into(savedMovieDetailPoster)
            binding.savedMovieDetailDescription.text = resultMovie.overview
        })
    }

    private fun provideViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(SavedMovieDetailViewModel::class.java)

    }
}