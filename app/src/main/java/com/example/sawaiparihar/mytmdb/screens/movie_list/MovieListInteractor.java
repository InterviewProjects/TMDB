package com.example.sawaiparihar.mytmdb.screens.movie_list;

import com.example.sawaiparihar.mytmdb.models.movie_list.MovieListModel;
import com.example.sawaiparihar.mytmdb.models.movie_list.Result;
import com.example.sawaiparihar.mytmdb.network.TMDBApi;
import com.example.sawaiparihar.mytmdb.screens.movie_list.util.PopularityComparator;
import com.example.sawaiparihar.mytmdb.screens.movie_list.util.RatingComparator;
import com.example.sawaiparihar.mytmdb.utils.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieListInteractor implements MovieListContract.Interacter {
    private CompositeDisposable mCompositeDisposable;
    private TMDBApi mTmdbApi;

    private MovieListModel mDataObject;
    private boolean mMovieListScreenActiviteStatus;

    public MovieListInteractor(CompositeDisposable mCompositeDisposable, TMDBApi mTmdbApi) {
        this.mCompositeDisposable = mCompositeDisposable;
        this.mTmdbApi = mTmdbApi;
        this.mMovieListScreenActiviteStatus = false;
    }

    @Override
    public void getMovieList(String filterType, final OnfetchMovieListFinishedListener listener) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("api_key", Constants.API_KEY);

        mCompositeDisposable.add(mTmdbApi.getMovieList(filterType, queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieListModel>() {
                    @Override
                    public void onNext(MovieListModel model) {
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
    public void setMovieListScreenActiveStatus(boolean status) {
        mMovieListScreenActiviteStatus = status;
    }

    @Override
    public boolean getMovieListScreenActiviteStatus() {
        return mMovieListScreenActiviteStatus;
    }

    @Override
    public void setData(MovieListModel model) {
        mDataObject = model;
    }

    @Override
    public MovieListModel fetchData() {
        return mDataObject;
    }

    @Override
    public void applyRatingSortFilters(List<Result> results, final OnFilterFinishedListener listener) {
        getRatingFilterdList(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Result>>() {
                    @Override
                    public void onNext(List<Result> results) {
                        listener.onFilterSuccess(results);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // not needed
                    }

                    @Override
                    public void onComplete() {
                        // not needed
                    }
                });
    }

    @Override
    public void applyPopularitySortFilters(List<Result> results, final OnFilterFinishedListener listener) {
        getPopularityFilterdList(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Result>>() {
                    @Override
                    public void onNext(List<Result> results) {
                        listener.onFilterSuccess(results);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // not needed
                    }

                    @Override
                    public void onComplete() {
                        // not needed
                    }
                });
    }

    private Observable<List<Result>> getRatingFilterdList(final List<Result> results){
        return Observable.create(new ObservableOnSubscribe<List<Result>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Result>> e) throws Exception {
                Collections.sort(results, new RatingComparator());

                e.onNext(results);
                e.onComplete();
            }
        });
    }

    private Observable<List<Result>> getPopularityFilterdList(final List<Result> results){
        return Observable.create(new ObservableOnSubscribe<List<Result>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Result>> e) throws Exception {
                Collections.sort(results, new PopularityComparator());

                e.onNext(results);
                e.onComplete();
            }
        });
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
