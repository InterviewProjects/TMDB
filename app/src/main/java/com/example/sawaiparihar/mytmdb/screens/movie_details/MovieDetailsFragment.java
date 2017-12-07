package com.example.sawaiparihar.mytmdb.screens.movie_details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sawaiparihar.mytmdb.R;
import com.example.sawaiparihar.mytmdb.TMDBApplication;
import com.example.sawaiparihar.mytmdb.base.WrapperFragment;
import com.example.sawaiparihar.mytmdb.models.movie_details.MovieDetailsModel;
import com.example.sawaiparihar.mytmdb.screens.movie_details.dagger.DaggerMovieDetailsComponent;
import com.example.sawaiparihar.mytmdb.screens.movie_details.dagger.MovieDetailsModule;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailsFragment extends WrapperFragment<MovieDetailsContract.Presenter> implements MovieDetailsContract.View, View.OnClickListener {
    @BindView(R.id.main_wrapper) ViewGroup mMainWrapper;
    @BindView(R.id.name_tv) TextView mNameTv;
    @BindView(R.id.poster_iv) ImageView mPosterIv;
    @BindView(R.id.favorite_iv) ImageView mFavoriteIv;
    @BindView(R.id.rating_tv) TextView mRatingTv;
    @BindView(R.id.over_view_tv) TextView mOverViewTv;
    @BindView(R.id.release_date_tv) TextView mReleaseDateTv;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private String mMovieId;
    private boolean mFavoriteState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize dagger
        DaggerMovieDetailsComponent.builder()
                .appComponent(((TMDBApplication) getActivity().getApplication()).getAppComponent())
                .movieDetailsModule(new MovieDetailsModule(this))
                .build()
                .inject(this);

        // set has option menu true
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // butter knife
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle("");
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mPresenter.requestMovieDetails(getArguments().getString("movie_id"));
    }

    @Override
    public void showLoader() {
        mMainWrapper.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        mMainWrapper.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(String title) {
        android.support.v7.app.ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) ab.setTitle(title);
    }

    @Override
    public void inflateData(MovieDetailsModel model, boolean isFavorite) {
        // load image
        Glide.with(getActivity())
                .load("http://image.tmdb.org/t/p/w185" + model.getPosterPath())
                .into(mPosterIv);

        // set title
        mNameTv.setText(model.getOriginalTitle());

        // set over view
        mOverViewTv.setText(model.getOverview());

        // set release date
        mReleaseDateTv.setText(model.getReleaseDate());

        // set rating
        mRatingTv.setText(String.valueOf(model.getVoteAverage()));

        // set favorite
        mMovieId = String.valueOf(model.getId());
        mFavoriteState = isFavorite;
        mFavoriteIv.setImageResource(isFavorite ? R.drawable.favorite : R.drawable.not_favorite);
        mFavoriteIv.setOnClickListener(this);

    }

    @Override
    public void updateFavoriteIcon(boolean isFavorite) {
        mFavoriteState = isFavorite;
        mFavoriteIv.setImageResource(isFavorite ? R.drawable.favorite : R.drawable.not_favorite);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorite_iv:
                mPresenter.favoriteIconClick(mFavoriteState, mMovieId);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
        }

        return true;
    }
}
