package com.example.myapplication.DetecTorBogGroma;

import android.content.Context;
import android.util.Log;

import org.tensorflow.lite.support.audio.TensorAudio;
import org.tensorflow.lite.task.audio.classifier.AudioClassifier;
import org.tensorflow.lite.task.audio.classifier.Classifications;
import org.tensorflow.lite.task.core.BaseOptions;

import java.io.IOException;
import java.util.List;

public class YamnetDetector {
    private static final int REQUIRED_SAMPLES = 15600;
    private static final float SNEEZE_THRESHOLD = 0.29f;
    private final AudioClassifier classifier;
    private final TensorAudio tensorAudio;
    private final short[] audioBuffer = new short[REQUIRED_SAMPLES];
    private int bufferPosition = 0;

    public YamnetDetector(Context context) throws IOException {
        BaseOptions baseOptions = BaseOptions.builder().setNumThreads(2).build();
        AudioClassifier.AudioClassifierOptions options = AudioClassifier.AudioClassifierOptions.builder().setBaseOptions(baseOptions).setMaxResults(10).build();
        classifier = AudioClassifier.createFromFileAndOptions(context, "yamnet.tflite", options);
        tensorAudio = classifier.createInputTensorAudio();
    }
    public Result detect(short[] samples) {
        if (samples == null || samples.length == 0) {
            return new Result(false);
        }
        int remaining = REQUIRED_SAMPLES - bufferPosition;
        int toCopy = Math.min(samples.length, remaining);
        System.arraycopy(samples, 0, audioBuffer, bufferPosition, toCopy);

        bufferPosition += toCopy;

        if (bufferPosition < REQUIRED_SAMPLES) {
            return new Result(false);
        }

        try {tensorAudio.load(audioBuffer);List<Classifications> results = classifier.classify(tensorAudio);
            float sneezeScore = 0f;
            for (Classifications classifications : results) {
                for (org.tensorflow.lite.support.label.Category category : classifications.getCategories()) {
                    String label = category.getLabel();
                    float score = category.getScore();
                    if (label != null && label.equalsIgnoreCase("Sneeze")) {
                        sneezeScore = Math.max(sneezeScore, score);
                    }
                }
            }
            bufferPosition = 0;

            return new Result(sneezeScore >= SNEEZE_THRESHOLD);

        } catch (Exception e) {
            bufferPosition = 0;
            return new Result(false);
        }
    }
    public void close() {
        if (classifier != null) {
            classifier.close();
        }
    }
}