package com.example.sawaiparihar.mytmdb.screens.movie_list;

import com.example.sawaiparihar.mytmdb.base.BaseInteractor;
import com.example.sawaiparihar.mytmdb.base.BasePresenter;
import com.example.sawaiparihar.mytmdb.models.movie_list.MovieListModel;
import com.example.sawaiparihar.mytmdb.models.movie_list.Result;

import java.util.List;

public interface MovieListContract {
    interface View {
        void showLoader();

        void hideLoader();

        void inflateData(List<Result> results);

        void openMovieDetailScreen(String movieId);
    }

    interface Presenter extends BasePresenter {
        void requestMovieList(String filterType);
        void localFilterClick(String filter);
        void movieItemClick(String id);
    }

    interface Interacter extends BaseInteractor {
        // fetch data from api callbacks
        interface OnfetchMovieListFinishedListener {
            void onApiSuccess(MovieListModel model);

            void onApiFailure(String error);
        }
        void getMovieList(String filterType, OnfetchMovieListFinishedListener listener);

        // movieList screen activite status
        void setMovieListScreenActiveStatus(boolean status);
        boolean getMovieListScreenActiviteStatus();

        // store and retrieve data locally callbacks
        void setData(MovieListModel model);
        MovieListModel fetchData();

        // filter data callbacks
        interface OnFilterFinishedListener {
            void onFilterSuccess(List<Result> result);
        }
        void applyRatingSortFilters(List<Result> results, OnFilterFinishedListener listener);
        void applyPopularitySortFilters(List<Result> results, OnFilterFinishedListener listener);
    }
}
