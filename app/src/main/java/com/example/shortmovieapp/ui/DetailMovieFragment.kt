package com.example.shortmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.shortmovieapp.R
import com.example.shortmovieapp.Util.Constans.Companion.POSTER_MAIN_URL
import com.example.shortmovieapp.databinding.FragmentDetailMovieBinding
import com.example.shortmovieapp.databinding.FragmentMainPageBinding
import com.example.shortmovieapp.model.Result
import com.example.shortmovieapp.viewModel.DetailMovieViewModel
import kotlinx.android.synthetic.main.fragment_detail_movie.*


class DetailMovieFragment : Fragment() {

    private var _binding : FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailMovieViewModel
    lateinit var resultMovie:Result

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val movieId: String? = args?.getString("movieId","databoss")

        provideViewModel()

        viewModel.getMovieDetail(movieId.toString())

        getDetailInformations()
    }

    private fun getDetailInformations() {
        viewModel._movieDetail.observe(this, Observer {
            it.let {
                resultMovie=it
                binding.movieDetailName.text = resultMovie.original_title
                binding.MovieDetailAvarage.text = resultMovie.vote_average.toString()+"/10"
                binding.MovieDetailRealizeDate.text = resultMovie.release_date
                val url= POSTER_MAIN_URL + resultMovie.poster_path
                Glide.with(this).load(url).into(binding.movieDetailPoster)
                binding.movieDetailDescription.text = resultMovie.overview
            }
        })
    }
    private fun provideViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(DetailMovieViewModel::class.java)
    }
}