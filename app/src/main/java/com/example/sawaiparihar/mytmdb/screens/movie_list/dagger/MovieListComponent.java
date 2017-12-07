package com.example.sawaiparihar.mytmdb.screens.movie_list.dagger;

import com.example.sawaiparihar.mytmdb.dagger.AppComponent;
import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListFragment;

import dagger.Component;

@MovieListCustomScope
@Component(dependencies = AppComponent.class, modules = {MovieListModule.class})
public interface MovieListComponent {
    void inject(MovieListFragment fragment);
}
