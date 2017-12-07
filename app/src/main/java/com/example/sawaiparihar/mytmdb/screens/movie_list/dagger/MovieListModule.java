package com.example.sawaiparihar.mytmdb.screens.movie_list.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.sawaiparihar.mytmdb.network.TMDBApi;
import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListAdapter;
import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListContract;
import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListInteractor;
import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


@Module
@MovieListCustomScope
public class MovieListModule {
    private final MovieListContract.View mView;

    public MovieListModule(MovieListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @MovieListCustomScope
    MovieListContract.Presenter providePresenter(MovieListContract.Interacter interacter) {
        return new MovieListPresenter(mView, interacter);
    }

    @Provides
    @MovieListCustomScope
    MovieListContract.View provideView() {
        return mView;
    }

    @Provides
    @MovieListCustomScope
    MovieListContract.Interacter provideMovieListInteractor(CompositeDisposable compositeDisposable, TMDBApi tmdbApi) {
        return new MovieListInteractor(compositeDisposable, tmdbApi);
    }

    @Provides
    @MovieListCustomScope
    MovieListAdapter provideMovieListAdapter(Context context) {
        return new MovieListAdapter(LayoutInflater.from(context));
    }
}
