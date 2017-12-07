/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.sawaiparihar.mytmdb.network;

import com.example.sawaiparihar.mytmdb.models.movie_details.MovieDetailsModel;
import com.example.sawaiparihar.mytmdb.models.movie_list.MovieListModel;
import com.google.gson.JsonObject;

import java.util.Map;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface TMDBApi {
    @GET("3/movie/{filter_type}")
    Observable<MovieListModel> getMovieList(@Path(value = "filter_type", encoded = true) String filterType, @QueryMap Map<String, String> queryMap);

    @GET("3/movie/{movie-id}")
    Observable<MovieDetailsModel> getMovieDetails(@Path(value = "movie-id", encoded = true) String movieId, @QueryMap Map<String, String> queryMap);
}
