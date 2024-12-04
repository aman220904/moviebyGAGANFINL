package com.example.movie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    private ListView favoritesListView;
    private Set<String> favoriteMovies;
    private ArrayAdapter<String> favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesListView = findViewById(R.id.favoritesListView);

        // Load favorite movies from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("movieApp", MODE_PRIVATE);
        favoriteMovies = preferences.getStringSet("favorites", new HashSet<>());

        if (favoritesListView != null) {
            favoritesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(favoriteMovies));
            favoritesListView.setAdapter(favoritesAdapter);
        } else {
            // Log error or handle the case where favoritesListView is not found
            Log.e("FavoritesActivity", "ListView not found!");
        }

        // Set an item click listener to handle deletion
        favoritesListView.setOnItemClickListener((parent, view, position, id) -> {
            String movieTitle = (String) parent.getItemAtPosition(position);
            showDeleteDialog(movieTitle);
        });
    }


    // Show confirmation dialog to delete a movie
    private void showDeleteDialog(String movieTitle) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Delete from Favorites")
                .setMessage("Do you want to delete this movie from your favorites?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteFromFavorites(movieTitle);
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Delete the movie from favorites
    private void deleteFromFavorites(String movieTitle) {
        // Remove the movie from the favorites set
        SharedPreferences preferences = getSharedPreferences("movieApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        favoriteMovies.remove(movieTitle);  // Remove movie from set
        editor.putStringSet("favorites", favoriteMovies);
        editor.apply();

        // Update the ListView
        favoritesAdapter.clear();
        favoritesAdapter.addAll(new ArrayList<>(favoriteMovies));
        favoritesAdapter.notifyDataSetChanged();

        // If there are no more favorites, show a message
        if (favoriteMovies.isEmpty()) {
            Toast.makeText(FavoritesActivity.this, "No favorite movies left", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(FavoritesActivity.this, "Movie removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }
}
