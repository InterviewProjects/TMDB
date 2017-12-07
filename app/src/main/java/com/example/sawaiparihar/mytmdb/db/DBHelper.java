package com.example.sawaiparihar.mytmdb.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sawaiparihar.mytmdb.models.db.FavouriteMovie;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MOVIES_INFO";

    public static final String MOVIE_TABLE_NAME = "movie_info";
    public static final String MOVIE_COLUMN_PRIMARY_KEY = "primary_id";
    public static final String MOVIE_COLUMN_ID = "movie_id";
    public static final String MOVIE_COLUMN_LIKED = "movie_like";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        tableCreateStatements(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(db);
    }

    private void tableCreateStatements(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + MOVIE_TABLE_NAME + "("
                            + MOVIE_COLUMN_PRIMARY_KEY + " INTEGER PRIMARY KEY, "
                            + MOVIE_COLUMN_ID + " VARCHAR(20), "
                            + MOVIE_COLUMN_LIKED + " VARCHAR(20) "
                            + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FavouriteMovie getFavouriteMovie(String movieId) throws Resources.NotFoundException, NullPointerException {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM "
                            + MOVIE_TABLE_NAME
                            + " WHERE "
                            + MOVIE_COLUMN_ID
                            + "= ?",
                    new String[]{movieId + ""});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                FavouriteMovie favouriteMovie = new FavouriteMovie();
                favouriteMovie.setMovieId(cursor.getString(cursor.getColumnIndex(MOVIE_COLUMN_ID)));
                favouriteMovie.setFavourite("1".equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(MOVIE_COLUMN_LIKED))));

                return favouriteMovie;
            } else {
                throw new Resources.NotFoundException("User with id " + movieId + " does not exists");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public Long insertFavouriteMovie(FavouriteMovie favouriteMovie) throws Exception {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MOVIE_COLUMN_ID, favouriteMovie.getMovieId());
            contentValues.put(MOVIE_COLUMN_LIKED, favouriteMovie.isFavourite());

            return db.insert(MOVIE_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteFavouriteMovie(FavouriteMovie favouriteMovie) throws Exception {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MOVIE_COLUMN_ID, favouriteMovie.getMovieId());
            contentValues.put(MOVIE_COLUMN_LIKED, favouriteMovie.isFavourite());

            db.delete(MOVIE_TABLE_NAME, MOVIE_COLUMN_ID + " = ?",
                    new String[] { favouriteMovie.getMovieId() });

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
