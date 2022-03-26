package com.example.shortmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shortmovieapp.R
import com.example.shortmovieapp.adapter.MovieAdapter
import com.example.shortmovieapp.databinding.FragmentMainPageBinding
import com.example.shortmovieapp.viewModel.MainPageViewModel


class MainPageFragment : Fragment() {

    private var _binding : FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: MainPageViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainPageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainPageViewModel::class.java)
        viewModel._movie.observe(this, Observer {
            adapter = MovieAdapter(it)
            binding.verticalRecyclerview.layoutManager = LinearLayoutManager(context)
            binding.verticalRecyclerview.adapter = adapter

        })
    }
}