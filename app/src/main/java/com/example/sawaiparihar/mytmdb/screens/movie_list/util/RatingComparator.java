package com.example.sawaiparihar.mytmdb.screens.movie_list.util;

import com.example.sawaiparihar.mytmdb.models.movie_list.Result;

import java.util.Comparator;

/**
 * Created by sawai.parihar on 05/12/17.
 */

public class RatingComparator implements Comparator<Result> {
    @Override
    public int compare(Result o1, Result o2) {
        int returnValue;
        if (o1.getVoteAverage() < o2.getVoteAverage()) {
            returnValue = 1;
        } else if (o1.getVoteAverage() > o2.getVoteAverage()) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }
}
