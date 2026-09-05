package com.example.myapplication.DetecTorBogGroma;
public class AnalyzeTempa {
    public boolean isTempMatchitsa(double[] scores) {
        if (scores == null || scores.length < 5) {
            return false;
        }
        int highCount = 0;
        int veryHighCount = 0;
        double maxScore = 0;
        for (double score : scores) {
            if (score >= 65) {
                highCount++;
            }
            if (score >= 80) {
                veryHighCount++;
            }
            if (score > maxScore) {
                maxScore = score;
            }
        }
        if (maxScore < 80) {
            return false;
        }
        if (highCount < 3) {
            return false;
        }
        return veryHighCount >= 1;
    }
}