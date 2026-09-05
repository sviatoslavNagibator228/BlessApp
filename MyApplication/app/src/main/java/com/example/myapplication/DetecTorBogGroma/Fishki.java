package com.example.myapplication.DetecTorBogGroma;

public class Fishki {
    public AudioFishki fishkiX(short[] samples) {

        if (samples == null || samples.length == 0) {
            return new AudioFishki(0, (short) 0, 0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
        double squareSum = 0.0;
        int zeroCrossings = 0;
        short peak = 0;
        int peakIndex = 0;
        double energySum = 0.0;
        double weightedEnergy = 0.0;
        double smoothCriminal = 0.0;
        int previousAbs = Math.abs(samples[0]);
        for (int i = 0; i < samples.length; i++) {
            short sample = samples[i];
            int abs = Math.abs(sample);
            squareSum += sample * sample;

            if (abs > peak) {
                peak = (short) abs;
                peakIndex = i;
            }
            if (i > 0) {
                boolean prevPositive = samples[i - 1] >= 0;
                boolean currentPositive = sample >= 0;

                if (prevPositive != currentPositive) {
                    zeroCrossings++;
                }
            }
            energySum += abs;
            weightedEnergy += abs * i;
            if (i > 0) {
                smoothCriminal += Math.abs(abs - previousAbs);
            }
            previousAbs = abs;
        }
        double rms = Math.sqrt(squareSum / samples.length);
        double zcr = (double) zeroCrossings / samples.length;
        double energyCenter = 0.0;

        if (energySum > 0) {
            energyCenter = weightedEnergy / energySum;
        }
        double threshold = peak * 0.2;
        int first = -1;
        int last = -1;
        int peakCount = 0;
        int peakWidth = 0;
        boolean insidePeak = false;
        int minPeakDistance = 50;
        int lastPeakIndex = -minPeakDistance;

        for (int i = 0; i < samples.length; i++) {
            int abs = Math.abs(samples[i]);
            if (abs >= threshold) {
                if (first == -1) {
                    first = i;
                }
                last = i;
                peakWidth++;
                if (!insidePeak && i - lastPeakIndex >= minPeakDistance) {
                    peakCount++;
                    lastPeakIndex = i;
                    insidePeak = true;
                }
            } else {
                insidePeak = false;
            }
        }
        int attack = 0;
        int decan = 0;
        int duration = 0;

        if (first != -1) {
            attack = peakIndex - first;
            decan = last - peakIndex;
            duration = last - first;
        }

        double spectralCentroid = calculateSpectralCentroid(samples);
        double spectralFlatness = calculateSpectralFlatness(samples);
        return new AudioFishki(rms, peak, zcr, peakIndex, attack, decan, duration, peakCount, peakWidth, energyCenter, smoothCriminal, spectralCentroid, spectralFlatness);
    }

    private double calculateSpectralCentroid(short[] samples) {
        int n = samples.length;
        if (n < 2) {
            return 0.0;
        }

        double[] real = new double[n];
        double[] imaginary = new double[n];
        for (int i = 0; i < n; i++) {
            real[i] = samples[i];
            imaginary[i] = 0.0;
        }
        fft(real, imaginary);

        double sampleRate = 16000.0;
        double frequencyStep = sampleRate / n;
        double weightedSum = 0.0;
        double magnitudeSum = 0.0;
        int half = n / 2;

        for (int i = 0; i <= half; i++) {
            double magnitude = Math.sqrt(real[i] * real[i] + imaginary[i] * imaginary[i]);
            double frequency = i * frequencyStep;
            weightedSum += frequency * magnitude;
            magnitudeSum += magnitude;
        }

        if (magnitudeSum == 0.0) {
            return 0.0;
        }
        return weightedSum / magnitudeSum;
    }


    private double calculateSpectralFlatness(short[] samples) {
        int n = samples.length;
        if (n < 2) {
            return 0.0;
        }

        double[] real = new double[n];
        double[] imaginary = new double[n];
        for (int i = 0; i < n; i++) {
            real[i] = samples[i];
            imaginary[i] = 0.0;
        }
        fft(real, imaginary);

        int half = n / 2;
        double logSum = 0.0;
        double arithmeticSum = 0.0;
        int count = 0;
        final double epsilon = 1e-12;

        for (int i = 0; i <= half; i++) {
            double magnitude = Math.sqrt(real[i] * real[i] + imaginary[i] * imaginary[i]);
            double power = magnitude * magnitude;

            power = Math.max(power, epsilon);
            logSum += Math.log(power);
            arithmeticSum += power;
            count++;
        }

        if (count == 0 || arithmeticSum == 0.0) {
            return 0.0;
        }
        double geometricMean = Math.exp(logSum / count);
        double arithmeticMean = arithmeticSum / count;

        return geometricMean / arithmeticMean;
    }
    private void fft(double[] real, double[] imaginary) {
        int n = real.length;
        int j = 0;

        for (int i = 1; i < n; i++) {
            int bit = n >> 1;
            while ((j & bit) != 0) {
                j ^= bit;
                bit >>= 1;
            }
            j ^= bit;

            if (i < j) {
                double temp = real[i];
                real[i] = real[j];
                real[j] = temp;
                temp = imaginary[i];
                imaginary[i] = imaginary[j];
                imaginary[j] = temp;
            }
        }


        for (int length = 2; length <= n; length <<= 1) {
            double angle = -2.0 * Math.PI / length;
            double wReal = Math.cos(angle);
            double wImaginary = Math.sin(angle);


            for (int i = 0; i < n; i += length) {
                double currentReal = 1.0;
                double currentImaginary = 0.0;
                int halfLength = length / 2;
                for (int k = 0; k < halfLength; k++) {
                    int evenIndex = i + k;
                    int oddIndex = i + k + halfLength;
                    double oddReal = real[oddIndex] * currentReal - imaginary[oddIndex] * currentImaginary;
                    double oddImaginary = real[oddIndex] * currentImaginary + imaginary[oddIndex] * currentReal;

                    real[oddIndex] = real[evenIndex] - oddReal;
                    imaginary[oddIndex] = imaginary[evenIndex] - oddImaginary;
                    real[evenIndex] += oddReal;
                    imaginary[evenIndex] += oddImaginary;

                    double nextReal = currentReal * wReal - currentImaginary * wImaginary;
                    double nextImaginary = currentReal * wImaginary + currentImaginary * wReal;

                    currentReal = nextReal;
                    currentImaginary = nextImaginary;
                }
            }
        }
    }
}



