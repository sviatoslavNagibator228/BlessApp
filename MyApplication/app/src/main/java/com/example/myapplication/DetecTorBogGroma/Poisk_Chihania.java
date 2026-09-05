package com.example.myapplication.DetecTorBogGroma;

import android.content.Context;

import java.io.IOException;

public class Poisk_Chihania {

    private final YamnetDetector yamnet;
    public Poisk_Chihania(Context context) throws IOException {
        yamnet = new YamnetDetector(context);
    }

    public Result detect(short[] samples) {
        if (samples == null || samples.length == 0) {
            return new Result(false);
        }
        return yamnet.detect(samples);
    }
    public void close() {
        yamnet.close();
    }
}



