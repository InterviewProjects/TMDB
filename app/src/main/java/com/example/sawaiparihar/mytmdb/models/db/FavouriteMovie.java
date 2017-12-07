package com.example.sawaiparihar.mytmdb.models.db;


public class FavouriteMovie {
    private boolean isFavourite;
    private String movieId;

    public FavouriteMovie() {
    }

    public FavouriteMovie(String movieId, boolean isFavourite) {
        this.movieId = movieId;
        this.isFavourite = isFavourite;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
