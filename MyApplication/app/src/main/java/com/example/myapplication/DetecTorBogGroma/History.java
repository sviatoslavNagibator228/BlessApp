package com.example.myapplication.DetecTorBogGroma;
import java.util.ArrayDeque;
import java.util.Deque;
public class History {
    private final int maxSize;
    private final Deque<Double> scores;
    public History(int maxSize) {
        this.maxSize = maxSize;
        this.scores = new ArrayDeque<>();
    }

    public void add(double score) {
        if (scores.size() == maxSize) {
            scores.removeFirst();
        }
        scores.addLast(score);
    }
    public double[] vArray() {
        double[] result = new double[scores.size()];
        int index = 0;
        for (double value : scores) {
            result[index++] = value;
        }
        return result;
    }
}
