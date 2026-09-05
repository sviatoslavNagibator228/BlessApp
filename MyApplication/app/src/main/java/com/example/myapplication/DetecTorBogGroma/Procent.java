package com.example.myapplication.DetecTorBogGroma;

public class Procent {
        public double PochitatProcent(AudioFishki fishki) {

            double procent = 0;

            if (fishki.getRms() >= 4500)
                procent += 14;

            if (fishki.getRms() >= 7000)
                procent += 4;

            if (fishki.getPeak() >= 10000)
                procent += 14;

            if (fishki.getPeak() >= 18000)
                procent += 4;

            if (fishki.getPeakCount() >= 16 && fishki.getPeakCount() <= 25)
                procent += 7;


            if (fishki.getPeakWidth() >= 300)
                procent += 10;

            if (fishki.getDecanSamples() >= 400 && fishki.getDecanSamples() <= 1000)
                procent += 7;

            if (fishki.getAttackSamples() >= 50 && fishki.getAttackSamples() <= 550)
                procent += 5;

            if (fishki.getEnergyCenter() >= 350 && fishki.getEnergyCenter() <= 560)
                procent += 5;

            if (fishki.getSmoothCriminal() >= 1300000)
                procent += 10;


            if (fishki.getSpectralCentroid() >= 2200)
                procent += 4;

            if (fishki.getSpectralCentroid() >= 3300)
                procent += 4;

            if (fishki.getZCR() >= 0.08 && fishki.getZCR() <= 0.30)
                procent += 3;


            if (fishki.getRms() < 1500)
                procent -= 20;

            if (fishki.getPeak() < 4000)
                procent -= 15;

            if (fishki.getDurationSamples() < 300)
                procent -= 15;

            if (fishki.getDecanSamples() < 100)
                procent -= 10;

            if (fishki.getPeakCount() > 35)
                procent -= 20;

            if (fishki.getPeakCount() > 70)
                procent -= 15;

            if (fishki.getPeakWidth() < 150)
                procent -= 10;

            if (fishki.getZCR() > 0.50)
                procent -= 12;

            if (fishki.getSpectralCentroid() < 1200)
                procent -= 10;

            procent = Math.max(0, Math.min(100, procent));

            return procent;
}}
