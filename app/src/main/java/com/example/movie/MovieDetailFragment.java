package com.example.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MovieDetailFragment extends Fragment {

    private TextView movieTitle, movieYear, movieRating, movieRuntime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        movieTitle = rootView.findViewById(R.id.movieTitle);
        movieYear = rootView.findViewById(R.id.movieYear);
        movieRating = rootView.findViewById(R.id.movieRating);
        movieRuntime = rootView.findViewById(R.id.movieRuntime);


        // Get movie data from arguments and set the values
        Bundle args = getArguments();
        if (args != null) {
            movieTitle.setText(args.getString("title"));
            movieYear.setText(args.getString("year"));
            movieRating.setText(args.getString("rating"));
            movieRuntime.setText(args.getString("runtime"));

        }

        return rootView;
    }
}
