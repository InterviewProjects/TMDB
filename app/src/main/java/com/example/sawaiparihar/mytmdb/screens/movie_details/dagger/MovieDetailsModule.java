package com.example.sawaiparihar.mytmdb.screens.movie_details.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.sawaiparihar.mytmdb.db.DBHelper;
import com.example.sawaiparihar.mytmdb.network.TMDBApi;
import com.example.sawaiparihar.mytmdb.screens.movie_details.MovieDetailsContract;
import com.example.sawaiparihar.mytmdb.screens.movie_details.MovieDetailsInteractor;
import com.example.sawaiparihar.mytmdb.screens.movie_details.MovieDetailsPresenter;
import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListAdapter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


@Module
@MovieDetailsCustomScope
public class MovieDetailsModule {
    private final MovieDetailsContract.View mView;

    public MovieDetailsModule(MovieDetailsContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @MovieDetailsCustomScope
    MovieDetailsContract.Presenter providePresenter(MovieDetailsContract.Interacter interacter) {
        return new MovieDetailsPresenter(mView, interacter);
    }

    @Provides
    @MovieDetailsCustomScope
    MovieDetailsContract.View provideView() {
        return mView;
    }

    @Provides
    @MovieDetailsCustomScope
    MovieDetailsContract.Interacter provideMovieListInteractor(CompositeDisposable compositeDisposable, TMDBApi tmdbApi, DBHelper dbHelper) {
        return new MovieDetailsInteractor(compositeDisposable, tmdbApi, dbHelper);
    }

    @Provides
    @MovieDetailsCustomScope
    MovieListAdapter provideMovieListAdapter(Context context) {
        return new MovieListAdapter(LayoutInflater.from(context));
    }

    @Provides
    @MovieDetailsCustomScope
    DBHelper provideDBHelper(Context context) {
        return new DBHelper(context);
    }
}
