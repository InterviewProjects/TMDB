package com.example.sawaiparihar.mytmdb.screens.movie_details;

import com.example.sawaiparihar.mytmdb.db.DBHelper;
import com.example.sawaiparihar.mytmdb.models.db.FavouriteMovie;
import com.example.sawaiparihar.mytmdb.models.movie_details.MovieDetailsModel;
import com.example.sawaiparihar.mytmdb.network.TMDBApi;
import com.example.sawaiparihar.mytmdb.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsInteractor implements MovieDetailsContract.Interacter {
    private CompositeDisposable mCompositeDisposable;
    private TMDBApi mTmdbApi;
    private DBHelper mDBHelper;

    public MovieDetailsInteractor(CompositeDisposable mCompositeDisposable, TMDBApi mTmdbApi, DBHelper mDBHelper) {
        this.mCompositeDisposable = mCompositeDisposable;
        this.mTmdbApi = mTmdbApi;
        this.mDBHelper = mDBHelper;
    }

    @Override
    public void getMovieDetails(String movieId, final OnfetchMovieListFinishedListener listener) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("api_key", Constants.API_KEY);

        mCompositeDisposable.add(mTmdbApi.getMovieDetails(movieId, queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieDetailsModel>() {
                    @Override
                    public void onNext(MovieDetailsModel model) {
                        listener.onApiSuccess(model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onApiFailure("some thing went wrong!");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public FavouriteMovie getFavoriteFromDB(String movieId) {
        FavouriteMovie favouriteMovie = null;
        try {
            favouriteMovie = mDBHelper.getFavouriteMovie(movieId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return favouriteMovie;
    }

    @Override
    public void storeFavoriteMovieIntoDB(String movieId) {
        try {
            mDBHelper.insertFavouriteMovie(new FavouriteMovie(movieId, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFavoriteMovieIntoDB(String movieId) {
        try {
            mDBHelper.deleteFavouriteMovie(new FavouriteMovie(movieId, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach() {
        // nothing now
    }

    @Override
    public void onDetach() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
