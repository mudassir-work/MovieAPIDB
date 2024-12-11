package com.example.database_recycler

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database_recycler.adapter.MovieAdapter
import com.example.database_recycler.auth.LoginActivity
import com.example.database_recycler.database.Movie
import com.example.database_recycler.databinding.ActivityMainBinding
import com.example.database_recycler.viewmodel.MovieViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

private lateinit var movieViewModel: MovieViewModel
private lateinit var adapter: MovieAdapter
    private lateinit var firebaseAuth: FirebaseAuth


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    firebaseAuth = FirebaseAuth.getInstance()

    // Initialize ViewModel
    movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]


//    // Set up RecyclerView
//    adapter = MovieAdapter(emptyList())
//    binding.recyclerView.adapter = adapter
//    binding.recyclerView.layoutManager = LinearLayoutManager(this)

    // Set up RecyclerView
    adapter = MovieAdapter(emptyList()) { movieToDelete ->
        movieViewModel.deleteMovie(movieToDelete) // Delete movie when callback is triggered
        Toast.makeText(this, "${movieToDelete.name} deleted.", Toast.LENGTH_SHORT).show()
    }
    binding.recyclerView.adapter = adapter
    binding.recyclerView.layoutManager = LinearLayoutManager(this)



//    // Observe LiveData to get movie list
//    movieViewModel.allMovies.observe(this) { movies ->
//        adapter = MovieAdapter(movies)
//        binding.recyclerView.adapter = adapter
//    }

    movieViewModel.allMovies.observe(this) { movies ->
        adapter = MovieAdapter(movies) { movieToDelete ->
            movieViewModel.deleteMovie(movieToDelete) // Call ViewModel to delete movie
            Toast.makeText(this, "${movieToDelete.name} deleted.", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adapter
    }



    binding.logoutButton.setOnClickListener {
        // Sign out the user and redirect to SignInActivity
        Toast.makeText(this, "Logged Out.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        firebaseAuth.signOut()// Close this activity so the user cannot return to it
    }


    // Add movie button click listener
    binding.addButton.setOnClickListener {
        val name = binding.nameInput.text.toString()
        val releaseYear = binding.releaseYearInput.text.toString()
        val cast = binding.castInput.text.toString()

        if (name.isNotBlank() && releaseYear.isNotBlank() && cast.isNotBlank()) {
            // Create Movie object and save to database
            val movie = Movie(name = name, releaseYear = releaseYear.toInt(), cast = cast)
            movieViewModel.addMovie(movie)
            Toast.makeText(this, "Movie Saved!", Toast.LENGTH_SHORT).show()

            // Optionally, clear the input fields after saving
            binding.nameInput.setText("")
            binding.releaseYearInput.setText("")
            binding.castInput.setText("")
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }


}
}







