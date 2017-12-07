package com.example.sawaiparihar.mytmdb;


import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListContract;
import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MovieListPresenterTest {
    MovieListContract.Presenter mPresenter;

    @Mock
    MovieListContract.View mView;

    @Mock
    MovieListContract.Interacter mInteractor;

    @Before
    public void setUp() throws Exception {
        mPresenter = new MovieListPresenter(mView, mInteractor);
    }

    @Test
    public void ratingFilterTest() throws Exception {
        mPresenter.movieItemClick("1234");
        verify(mView).openMovieDetailScreen("1234");
    }
}
