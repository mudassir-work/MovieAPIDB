package com.example.database_recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.database_recycler.R
import com.example.database_recycler.database.Movie
import com.example.database_recycler.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class MovieAdapter(val movieList: List<Movie>, private val onDeleteClick: (Movie) -> Unit ) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val expandedPositionSet = mutableSetOf<Int>()

    inner class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }





    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        val isExpanded = expandedPositionSet.contains(position)
        with(holder.binding) {
            movieName.setTextColor(android.graphics.Color.BLACK)
            releaseYear.setTextColor(android.graphics.Color.BLACK)
            cast.setTextColor(android.graphics.Color.BLACK)
            story.setTextColor(android.graphics.Color.BLACK)
            rating.setTextColor(android.graphics.Color.BLACK)
            movieName.text = movie.name
            releaseYear.text = "Release Year: ${movie.releaseYear}"
            cast.text = "Cast: ${movie.cast}"
            story.text = "Story: ${movie.story ?: "No Story found for such Movie"}"
            rating.text = "Rating: ${movie.rating ?: "N/A"}"
            // Show or hide the expanded details
            detailsLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            // Show or hide the poster image
            if (isExpanded) {
                moviePoster.visibility = View.VISIBLE
                Picasso.get()
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.error_image) // Optional: Placeholder
//                    .error(R.drawable.error_image)             // Optional: Error image
                    .into(moviePoster)
            } else {
                moviePoster.visibility = View.GONE
            }

            // Handle click to expand/collapse
            root.setOnClickListener {
                if (isExpanded) expandedPositionSet.remove(position) else expandedPositionSet.add(position)
                notifyItemChanged(position)
            }

            // Delete button action
            deleteButton.setOnClickListener {
                onDeleteClick(movie) // Trigger callback to delete the movie
            }

        }
    }






    override fun getItemCount(): Int = movieList.size
}








