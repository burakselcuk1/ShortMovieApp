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
import com.example.shortmovieapp.Util.Constans.Companion.POSTER_MAIN_URL
import com.example.shortmovieapp.model.Movie
import kotlinx.android.synthetic.main.single_movie_item.view.*


class MovieAdapter(private val dataSet: Movie) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView
        val movieDescription: TextView
        val movieRealizeDate: TextView

        init {
            // Define click listener for the ViewHolder's View.
            movieName = view.findViewById(R.id.movieName)
            movieDescription = view.findViewById(R.id.movieDescription)
            movieRealizeDate = view.findViewById(R.id.movieRealizeDate)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.single_movie_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.movieName.text = dataSet.results[position].original_title
        viewHolder.movieDescription.text = dataSet.results[position].overview
        viewHolder.movieRealizeDate.text = dataSet.results[position].release_date

        val url = POSTER_MAIN_URL + dataSet.results[position].poster_path
        viewHolder.itemView.apply {
            Glide.with(this).load(url).into(viewHolder.itemView.movieImage)
        }

        viewHolder.itemView.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable("movieId",""+dataSet.results.get(position).id)
            val navigationController = Navigation.findNavController(viewHolder.itemView)
            navigationController.navigate(R.id.action_mainPageFragment_to_detailMovieFragment,bundle!!)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.results.size

}
