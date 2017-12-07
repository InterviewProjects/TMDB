package com.example.sawaiparihar.mytmdb.screens.movie_details;

import com.example.sawaiparihar.mytmdb.base.WrapperPresenter;
import com.example.sawaiparihar.mytmdb.models.db.FavouriteMovie;
import com.example.sawaiparihar.mytmdb.models.movie_details.MovieDetailsModel;

public class MovieDetailsPresenter extends WrapperPresenter<MovieDetailsContract.View>
        implements MovieDetailsContract.Presenter, MovieDetailsContract.Interacter.OnfetchMovieListFinishedListener {
    private MovieDetailsContract.Interacter mInteractor;

    public MovieDetailsPresenter(MovieDetailsContract.View mView, MovieDetailsContract.Interacter mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void requestMovieDetails(String movieId) {
        mView.showLoader();
        mInteractor.getMovieDetails(movieId, this);
    }

    @Override
    public void favoriteIconClick(boolean currentState, String movieId) {
        mView.updateFavoriteIcon(!currentState);

        if (currentState) {
            mInteractor.deleteFavoriteMovieIntoDB(movieId);
        } else {
            mInteractor.storeFavoriteMovieIntoDB(movieId);
        }
    }

    @Override
    public void onAttach() {
        if (mInteractor != null) {
            mInteractor.onAttach();
        }
    }

    @Override
    public void onDetach() {
        if (mInteractor != null) {
            mInteractor.onDetach();
        }
    }

    @Override
    public void onApiSuccess(MovieDetailsModel model) {
        // hide loader
        mView.hideLoader();

        // ask view to set title
        mView.setTitle(model.getOriginalTitle());

        // ask view to inflate data
        FavouriteMovie favouriteMovie = mInteractor.getFavoriteFromDB(String.valueOf(model.getId()));
        boolean isFavorite = favouriteMovie != null && favouriteMovie.isFavourite();
        mView.inflateData(model, isFavorite);
    }

    @Override
    public void onApiFailure(String error) {
        // hide loader
        mView.hideLoader();
    }
}
