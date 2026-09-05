package com.example.myapplication.DetecTorBogGroma;

public class AudioFishki {
    private final double rms;
    private final short peak;
    private final double zcr;
    private final int attackSamples;
    private final int decanSamples;
    private final int durationSamples;
    private final int peakCount;
    private final int peakWidth;
    private final double energyCenter;
    private final double smoothCriminal;
    private final int peakIndex;
    private final double spectralCentroid;
    private final double spectralFlatness;
    public AudioFishki(double rms, short peak, double zcr, int peakIndex, int attackSamples, int decanSamples, int durationSamples, int peakCount, int peakWidth, double energyCenter, double smoothCriminal, double spectralCentroid, double spectralFlatness) {
        this.rms = rms;
        this.peak = peak;
        this.zcr = zcr;
        this.peakIndex = peakIndex;
        this.attackSamples = attackSamples;
        this.decanSamples = decanSamples;
        this.durationSamples = durationSamples;
        this.peakCount = peakCount;
        this.peakWidth = peakWidth;
        this.energyCenter = energyCenter;
        this.smoothCriminal = smoothCriminal;
        this.spectralCentroid = spectralCentroid;
        this.spectralFlatness = spectralFlatness;
    }

    public double getRms() {
        return rms;
    }
    public short getPeak() {
        return peak;
    }
    public double getZCR() {
        return zcr;
    }
    public int getAttackSamples() {
        return attackSamples;
    }

    public int getDecanSamples() {
        return decanSamples;
    }

    public int getDurationSamples() {
        return durationSamples;
    }

    public int getPeakCount() {
        return peakCount;
    }

    public int getPeakWidth() {
        return peakWidth;
    }

    public double getEnergyCenter() {
        return energyCenter;
    }

    public double getSmoothCriminal() {
        return smoothCriminal;
    }
    public double getSpectralCentroid() {return spectralCentroid;}
    public double getSpectralFlatness() {return spectralFlatness;}
}
