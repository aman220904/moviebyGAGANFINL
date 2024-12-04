package com.example.movie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerViewMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;  // Movie data list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);

        // Set up RecyclerView with an Adapter
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, this::onMovieClick);  // Pass a click handler to the adapter
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(movieAdapter);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Search button click listener
        searchButton.setOnClickListener(v -> {
            String searchQuery = searchEditText.getText().toString().trim();
            if (!searchQuery.isEmpty()) {
                performSearch(searchQuery);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to perform search
    private void performSearch(String query) {
        // Here, you should call an API or filter a list of movie data based on the search query.
        // For now, let's mock some search results.

        // Example search results (this should be replaced by actual API data)
        List<Movie> searchedMovies = new ArrayList<>();
        searchedMovies.add(new Movie("The Matrix", "1999", "8.7", "136 min"));
        searchedMovies.add(new Movie("Inception", "2010", "8.8", "148 min"));
        searchedMovies.add(new Movie("The Dark Knight", "2008", "9.0", "152 min"));
        searchedMovies.add(new Movie("Forrest Gump", "1994", "8.8", "142 min"));
        searchedMovies.add(new Movie("The Shawshank Redemption", "1994", "9.3", "142 min"));
        searchedMovies.add(new Movie("Pulp Fiction", "1994", "8.9", "154 min"));
        searchedMovies.add(new Movie("Fight Club", "1999", "8.8", "139 min"));
        searchedMovies.add(new Movie("Avatar", "2009", "7.8", "162 min"));
        searchedMovies.add(new Movie("The Godfather", "1972", "9.2", "175 min"));
        searchedMovies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "2001", "8.8", "178 min"));
        searchedMovies.add(new Movie("Gladiator", "2000", "8.5", "155 min"));
        searchedMovies.add(new Movie("Interstellar", "2014", "8.6", "169 min"));
        searchedMovies.add(new Movie("The Avengers", "2012", "8.0", "143 min"));
        searchedMovies.add(new Movie("Titanic", "1997", "7.8", "195 min"));
        searchedMovies.add(new Movie("Jurassic Park", "1993", "8.1", "127 min"));
        searchedMovies.add(new Movie("Star Wars: A New Hope", "1977", "8.6", "121 min"));
        searchedMovies.add(new Movie("The Lion King", "1994", "8.5", "88 min"));
        searchedMovies.add(new Movie("The Prestige", "2006", "8.5", "130 min"));
        searchedMovies.add(new Movie("The Social Network", "2010", "7.7", "120 min"));

        // Update the RecyclerView with search results
        movieList.clear();
        movieList.addAll(searchedMovies);
        movieAdapter.notifyDataSetChanged();
    }

    // Method to create options menu (like the help menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Handle options menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorites) {
            // Navigate to FavoritesActivity
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            showHelpDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    // Method to show help dialog
    private void showHelpDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Help")
                .setMessage("This is a Movie App. You can search for movies, add them to your collection, and view detailed information. Use the buttons to perform these actions.")
                .setPositiveButton("OK", null)
                .show();
    }

    // Handle the movie item click and show the dialog to add to favorites
    private void onMovieClick(Movie movie) {
        new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("Add to Favorites")
                .setMessage("Do you want to add this movie to your favorites?")
                .setPositiveButton("Yes", (dialog, which) -> addToFavorites(movie))
                .setNegativeButton("No", null)
                .show();
    }

    // Add the movie to favorites in SharedPreferences
    private void addToFavorites(Movie movie) {
        SharedPreferences preferences = getSharedPreferences("movieApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Save the movie title to a set (or movie ID, for better storage)
        Set<String> favorites = preferences.getStringSet("favorites", new HashSet<>());
        favorites.add(movie.getTitle());  // You could store the entire movie object as JSON here
        editor.putStringSet("favorites", favorites);
        editor.apply();

        Toast.makeText(MainActivity.this, "Movie added to favorites!", Toast.LENGTH_SHORT).show();
    }
}
