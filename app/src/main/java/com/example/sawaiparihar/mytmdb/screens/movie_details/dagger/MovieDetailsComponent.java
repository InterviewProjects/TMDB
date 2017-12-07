package com.example.sawaiparihar.mytmdb.screens.movie_details.dagger;

import com.example.sawaiparihar.mytmdb.dagger.AppComponent;
import com.example.sawaiparihar.mytmdb.screens.movie_details.MovieDetailsFragment;

import dagger.Component;

@MovieDetailsCustomScope
@Component(dependencies = AppComponent.class, modules = {MovieDetailsModule.class})
public interface MovieDetailsComponent {
    void inject(MovieDetailsFragment fragment);
}
