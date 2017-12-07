package com.example.sawaiparihar.mytmdb.dagger;

import android.content.Context;

import com.example.sawaiparihar.mytmdb.db.DBHelper;
import com.example.sawaiparihar.mytmdb.network.TMDBApi;
import com.example.sawaiparihar.mytmdb.screens.movie_details.MovieDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
@Component(modules = {AppModule.class, CommonModule.class, NetworkModule.class})
public interface AppComponent {
    TMDBApi tmdbApi();
    CompositeDisposable compositeDisposable();
    Context context();
}
