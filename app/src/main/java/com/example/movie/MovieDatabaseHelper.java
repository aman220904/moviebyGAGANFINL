package com.example.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIES = "movies";

    // Movie table columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_RUNTIME = "runtime";
    private static final String COLUMN_ACTORS = "actors";
    private static final String COLUMN_PLOT = "plot";
    private static final String COLUMN_POSTER_URL = "poster_url";

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_YEAR + " TEXT, " +
                COLUMN_RATING + " TEXT, " +
                COLUMN_RUNTIME + " TEXT, " +
                COLUMN_ACTORS + " TEXT, " +
                COLUMN_PLOT + " TEXT, " +
                COLUMN_POSTER_URL + " TEXT)";
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_RUNTIME, movie.getRuntime());


        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }

    public void deleteMovie(int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, COLUMN_ID + " = ?", new String[]{String.valueOf(movieId)});
        db.close();
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_MOVIES, null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                    String year = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YEAR));
                    String rating = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RATING));
                    String runtime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUNTIME));

                    movies.add(new Movie(title, year, rating, runtime));
                } while (cursor.moveToNext());
            }}
        return movies;
    }
}
