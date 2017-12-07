package com.example.sawaiparihar.mytmdb;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.example.sawaiparihar.mytmdb.screens.movie_list.MovieListFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class MovieListAndroidTest {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);
    private MovieListFragment mMovieListFragment;

    @Before
    public void setUp() throws Exception {
        this.activityTestRule.launchActivity(new Intent());
        this.mMovieListFragment = ((MovieListFragment) this.activityTestRule.getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.main_container));
    }

    @Test
    public void testShowItem() {
        View viewTest = mMovieListFragment.getView().findViewById(R.id.recyler_view);
        assertThat(viewTest, notNullValue());
        assertThat(viewTest, instanceOf(RecyclerView.class));
    }
}
