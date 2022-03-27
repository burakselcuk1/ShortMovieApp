package com.example.shortmovieapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shortmovieapp.R
import com.example.shortmovieapp.Util.Constans.Companion.POSTER_MAIN_URL
import kotlinx.android.synthetic.main.saved_movie_single_item.view.*
import com.example.shortmovieapp.model.Result

class SavedMovieAdapter(private val dataSet: ArrayList<Result>) :
    RecyclerView.Adapter<SavedMovieAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView
        val overview: TextView
        val roomMovieImage: ImageView
        val realizeDate: TextView
        init {
            // Define click listener for the ViewHolder's View.
            movieName = view.findViewById(R.id.savedMovieName)
            overview = view.findViewById(R.id.savedMovieDescription)
            roomMovieImage = view.findViewById(R.id.savedMovieImage)
            realizeDate = view.findViewById(R.id.savedMovieRealizeDate)
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.saved_movie_single_item, viewGroup, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.movieName.text = dataSet[position].original_title
        viewHolder.overview.text = dataSet[position].overview
        viewHolder.realizeDate.text = dataSet[position].release_date

        val url =POSTER_MAIN_URL +  dataSet.get(position).poster_path

        viewHolder.itemView.apply {
            Glide.with(this).load(url).into(viewHolder.itemView.savedMovieImage)
        }

        val result: Result = dataSet.get(position)
        viewHolder.overview.text = result.overview
        viewHolder.movieName.text = result.original_title

        // Goes to SavedMovieDetailFragment
        viewHolder.itemView.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("movieIdd", ""+result.id)

            val navigationController = Navigation.findNavController(viewHolder.itemView)
            navigationController.navigate(R.id.action_savedMovieFragment_to_savedMovieDetailFragment,bundle!!)
        }
    }
    override fun getItemCount() = dataSet.size
}