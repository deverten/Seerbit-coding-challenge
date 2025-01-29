package org.seerbit.algorithm;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmSolutions {

    /**
     * Problem 1: Merge Overlapping Intervals
     * Given a sorted list of intervals, merge overlapping intervals and return the result.
     *
     * @param intervals List of intervals where each interval is represented as [start, end]
     * @return List of merged intervals
     */

    public static List<int[]> doMergeIntervals(int[][] intervals) {
        List<int[]> mergedIntervals = new ArrayList<>();

        if (intervals == null || intervals.length == 0) {
            return mergedIntervals;
        }

        // Start with the first interval
        int[] currentInterval = intervals[0];
        mergedIntervals.add(currentInterval);

        for (int i = 1; i < intervals.length; i++) {
            int currentEnd = currentInterval[1];
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];

            if (nextStart <= currentEnd) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            }
            else {
                currentInterval = intervals[i];
                mergedIntervals.add(currentInterval);
            }
        }

        return mergedIntervals;
    }
}