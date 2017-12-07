package com.example.sawaiparihar.mytmdb.screens.movie_list;

import com.example.sawaiparihar.mytmdb.base.WrapperPresenter;
import com.example.sawaiparihar.mytmdb.models.movie_list.MovieListModel;
import com.example.sawaiparihar.mytmdb.models.movie_list.Result;
import java.util.List;

import static com.example.sawaiparihar.mytmdb.utils.Constants.SORT_POPULARITY;
import static com.example.sawaiparihar.mytmdb.utils.Constants.SORT_RATING;

public class MovieListPresenter extends WrapperPresenter<MovieListContract.View>
        implements MovieListContract.Presenter, MovieListContract.Interacter.OnfetchMovieListFinishedListener, MovieListContract.Interacter.OnFilterFinishedListener {
    private MovieListContract.Interacter mInteractor;

    public MovieListPresenter(MovieListContract.View mView, MovieListContract.Interacter mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void requestMovieList(String filterType) {
        if (!mInteractor.getMovieListScreenActiviteStatus()) {
            mInteractor.setMovieListScreenActiveStatus(true);

            mView.showLoader();
            mInteractor.getMovieList(filterType, this);
        }
    }

    @Override
    public void localFilterClick(String filter) {
        switch (filter) {
            case SORT_RATING:
                if (mInteractor.fetchData() != null) {
                    mInteractor.applyRatingSortFilters(mInteractor.fetchData().getResults(), this);
                }
                break;

            case SORT_POPULARITY:
                if (mInteractor.fetchData() != null) {
                    mInteractor.applyPopularitySortFilters(mInteractor.fetchData().getResults(), this);
                }
                break;
        }
    }

    @Override
    public void movieItemClick(String id) {
        mView.openMovieDetailScreen(id);
    }

    @Override
    public void onAttach() {
        if (mInteractor != null) {
            mInteractor.onAttach();
        }
    }

    @Override
    public void onDetach() {
        if (mInteractor != null) {
            mInteractor.onDetach();
        }
    }

    @Override
    public void onApiSuccess(MovieListModel movieListModel) {
        // hide loader
        mView.hideLoader();

        // store data locally
        mInteractor.setData(movieListModel);

        // ask view to inflate data
        mView.inflateData(movieListModel.getResults());
    }

    @Override
    public void onApiFailure(String error) {
        // hide loader
        mView.hideLoader();
    }

    @Override
    public void onFilterSuccess(List<Result> result) {
        // ask view to inflate data
        mView.inflateData(result);
    }
}
