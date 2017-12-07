package com.example.sawaiparihar.mytmdb.screens.movie_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sawaiparihar.mytmdb.R;
import com.example.sawaiparihar.mytmdb.models.movie_list.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Result> mResults;
    private MovieListAdapterCallbacks mCallback;

    public interface MovieListAdapterCallbacks {
        void onItemClick(Result result);
    }

    public MovieListAdapter(LayoutInflater inflater) {
        this.mLayoutInflater = inflater;
    }

    @Override
    public MovieListAdapter.MovieItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieItemHolder(mLayoutInflater.inflate(R.layout.adapter_movie_list_item, null));
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.MovieItemHolder holder, int position) {
        holder.bindData(mResults.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    void setResults(List<Result> results) {
        this.mResults = results;
    }

    void setMovieListAdapterCallback(MovieListAdapterCallbacks callback) {
        this.mCallback = callback;
    }

    class MovieItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_iv) ImageView imageIcon;
        @BindView(R.id.rating_tv) TextView ratingTv;
        @BindView(R.id.name_tv) TextView nameTv;

        MovieItemHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindData(Result result, int position) {
            if (result != null) {

                // set image url
                Glide.with(itemView.getContext())
                        .load("http://image.tmdb.org/t/p/w185" + result.getPosterPath())
                        .into(imageIcon);

                // set title
                nameTv.setText(result.getTitle());

                // set rating
                ratingTv.setText(String.valueOf(result.getVoteAverage()));

                // on click
                itemView.setTag(result);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            // notify parent about click
            if (mCallback != null) mCallback.onItemClick((Result) v.getTag());
        }
    }
}
