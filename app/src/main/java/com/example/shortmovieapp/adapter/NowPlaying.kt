package com.example.shortmovieapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shortmovieapp.R
import com.example.shortmovieapp.Util.Constans
import com.example.shortmovieapp.model.movie
import kotlinx.android.synthetic.main.single_movie_item.view.*
import kotlinx.android.synthetic.main.single_movie_item_now_playing.view.*


class NowPlaying(private val dataSet: movie) :
    RecyclerView.Adapter<NowPlaying.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nowPlayingMovieName: TextView
        val nowPlayingMovieDescription: TextView

        init {
            // Define click listener for the ViewHolder's View.
            nowPlayingMovieName = view.findViewById(R.id.nowPlayingMovieName)
            nowPlayingMovieDescription = view.findViewById(R.id.nowPlayingMovieDescription)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.single_movie_item_now_playing, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.nowPlayingMovieName.text = dataSet.results[position].original_title
        viewHolder.nowPlayingMovieDescription.text = dataSet.results[position].overview

        val url = Constans.POSTER_MAIN_URL + dataSet.results[position].poster_path
        viewHolder.itemView.apply {
            Glide.with(this).load(url).into(viewHolder.itemView.nowPlayingMoviePoster)
        }

        viewHolder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("movieId",""+dataSet.results.get(position).id)
            val navigationController = Navigation.findNavController(viewHolder.itemView)
            navigationController.navigate(R.id.action_mainPageFragment_to_savedMovieFragment,bundle!!)
        }
    }

    override fun getItemCount() = dataSet.results.size

}
