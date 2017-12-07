package com.example.sawaiparihar.mytmdb.screens.movie_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sawaiparihar.mytmdb.R;
import com.example.sawaiparihar.mytmdb.TMDBApplication;
import com.example.sawaiparihar.mytmdb.base.WrapperFragment;
import com.example.sawaiparihar.mytmdb.models.movie_list.Result;
import com.example.sawaiparihar.mytmdb.screens.movie_details.MovieDetailsFragment;
import com.example.sawaiparihar.mytmdb.screens.movie_list.dagger.DaggerMovieListComponent;
import com.example.sawaiparihar.mytmdb.screens.movie_list.dagger.MovieListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sawaiparihar.mytmdb.utils.Constants.SORT_POPULARITY;
import static com.example.sawaiparihar.mytmdb.utils.Constants.SORT_RATING;

public class MovieListFragment extends WrapperFragment<MovieListContract.Presenter> implements MovieListContract.View,
        MovieListAdapter.MovieListAdapterCallbacks {

    @BindView(R.id.recyler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    @Inject
    MovieListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize dagger
        DaggerMovieListComponent.builder()
                .appComponent(((TMDBApplication) getActivity().getApplication()).getAppComponent())
                .movieListModule(new MovieListModule(this))
                .build()
                .inject(this);

        // set has option menu true
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // butter knife
        ButterKnife.bind(this, view);

        // initialize recycler view and adapter
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter.setMovieListAdapterCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // set action bar title
        android.support.v7.app.ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Home");
            ab.setDisplayHomeAsUpEnabled(false);
        }

        // request movie list with default filter
        mPresenter.requestMovieList("upcoming");
    }

    @Override
    public void showLoader() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void inflateData(List<Result> results) {
        mAdapter.setResults(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void openMovieDetailScreen(String movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("movie_id", movieId);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case SORT_RATING:
                mPresenter.localFilterClick(SORT_RATING);
                break;

            case SORT_POPULARITY:
                mPresenter.localFilterClick(SORT_POPULARITY);
                break;
        }

        return true;
    }

    @Override
    public void onItemClick(Result result) {
        mPresenter.movieItemClick(String.valueOf(result.getId()));
    }
}
