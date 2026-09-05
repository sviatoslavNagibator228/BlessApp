package com.example.myapplication.DetecTorBogGroma;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import androidx.core.content.ContextCompat;
public class Recorder {
    public static final int bufferSize = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
    private AudioRecord audioRecord;
    private Thread recordingThread;
    private boolean isRecording = false;
    private final Context constantin;
    private final Proslushka proslushka;

    public Recorder(Context constantin, Proslushka proslushka) {
        this.constantin = constantin;
        this.proslushka = proslushka;
    }

    public void start() {
        if (ContextCompat.checkSelfPermission(constantin, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        audioRecord.startRecording();
        isRecording = true;
        recordingThread = new Thread(() -> {
            short[] buffer = new short[1024];
            while (isRecording) {
                int read = audioRecord.read(buffer, 0, buffer.length);
                if (read > 0) {
                    short[] frame = new short[read];
                    System.arraycopy(buffer, 0, frame, 0, read);
                    proslushka.sluhka(frame);
                }

            }

        });
        recordingThread.start();
    }

    public void stop() {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }
}