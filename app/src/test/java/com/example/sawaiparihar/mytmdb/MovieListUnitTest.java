package com.example.sawaiparihar.mytmdb;

import android.test.suitebuilder.annotation.MediumTest;

import com.example.sawaiparihar.mytmdb.models.movie_list.Result;
import com.example.sawaiparihar.mytmdb.screens.movie_list.util.RatingComparator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MovieListUnitTest {
    @Test
    public void ratingFilteredListTest() {
        List<Result> results = new ArrayList<>();
        results.add(new Result(1d));
        results.add(new Result(4d));
        results.add(new Result(2d));
        results.add(new Result(3d));

        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new Result(4d));
        expectedResults.add(new Result(3d));
        expectedResults.add(new Result(2d));
        expectedResults.add(new Result(1d));

        Collections.sort(results, new RatingComparator());
        for (int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            Result testResult = expectedResults.get(i);

            assertEquals(result.getVoteAverage(), testResult.getVoteAverage());
        }
    }
}
