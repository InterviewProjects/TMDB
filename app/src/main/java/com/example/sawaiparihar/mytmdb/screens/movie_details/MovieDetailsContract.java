package com.example.sawaiparihar.mytmdb.screens.movie_details;

import com.example.sawaiparihar.mytmdb.base.BaseInteractor;
import com.example.sawaiparihar.mytmdb.base.BasePresenter;
import com.example.sawaiparihar.mytmdb.models.db.FavouriteMovie;
import com.example.sawaiparihar.mytmdb.models.movie_details.MovieDetailsModel;

public interface MovieDetailsContract {
    interface View {
        void showLoader();

        void hideLoader();

        void setTitle(String title);

        void inflateData(MovieDetailsModel results, boolean isFavorite);

        void updateFavoriteIcon(boolean state);
    }

    interface Presenter extends BasePresenter {
        void requestMovieDetails(String movieId);
        void favoriteIconClick(boolean currentState, String movieId);
    }

    interface Interacter extends BaseInteractor {
        // fetch data from api callbacks
        interface OnfetchMovieListFinishedListener {
            void onApiSuccess(MovieDetailsModel model);

            void onApiFailure(String error);
        }
        void getMovieDetails(String movieId, OnfetchMovieListFinishedListener listener);

        FavouriteMovie getFavoriteFromDB(String movieId);
        void storeFavoriteMovieIntoDB(String movieId);
        void deleteFavoriteMovieIntoDB(String movieId);
    }
}
