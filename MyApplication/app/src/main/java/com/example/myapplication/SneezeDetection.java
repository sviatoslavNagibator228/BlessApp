package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.DetecTorBogGroma.Info;
import com.example.myapplication.DetecTorBogGroma.Poisk_Chihania;
import com.example.myapplication.DetecTorBogGroma.Recorder;
import com.example.myapplication.DetecTorBogGroma.Result;
import com.example.myapplication.DetecTorBogGroma.YamnetDetector;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class SneezeDetection extends Service {
    DateTimeFormatter currentTime;
    public static LocalTime now;
    private Recorder recorder;
    private Random rnd = new Random();
    Handler handler = new Handler();
    public static LocalTime am;
    public static LocalTime pm;
   // private YamnetDetector detecTor;//
    private int b;
    private Poisk_Chihania poiskChihania;
    @Override
    public void onCreate() {
        super.onCreate();
      //  detecTor = new YamnetDetector(this);//
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, "sneeze_channel").setContentTitle("Детектор чиха").setContentText("Детектор работает").setSmallIcon(R.mipmap.ic_launcher).setOngoing(true).build();
        startForeground(1, notification);
        try {
            poiskChihania = new Poisk_Chihania(this);
        } catch (Exception e) {
            System.out.println("0");
            System.out.println("0");
            System.out.println("0");
            System.out.println("0");
            e.printStackTrace();
            stopSelf();
            return;
        }
        currentTime = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
        recorder = new Recorder(this, samples -> {
            Result result = poiskChihania.detect(samples);
           // Poisk_Chihania poiskChihania = new Poisk_Chihania();
            now = LocalTime.now();
           try{
               am = LocalTime.parse(Info.am + " AM", currentTime);}
           catch(Exception e){
               am = LocalTime.parse("00:01 AM", currentTime);
                }
           try{
               pm = LocalTime.parse(Info.pm + " PM", currentTime);}
           catch (Exception e){
               pm = LocalTime.parse("11:59 PM", currentTime);
           }
                if (result.isBilLiSneeze() && Info.is == true && Info.hranilishe.getInt("currentBless", 5) > 0 && Info.bibibibBisnesmanVozvrashenie && !now.isBefore(am) && !now.isAfter(pm) && Info.mediaPlayer[0] != null && Info.mediaPlayer[1] != null && Info.mediaPlayer[2] != null && Info.mediaPlayer[3] != null && Info.mediaPlayer[4] != null) {
                    Info.bibibibBisnesmanVozvrashenie=false;
                    Info.currentBless = Info.hranilishe.getInt("currentBless", 5);
                    Info.currentBless--;
                    Info.hranilishe.edit().putInt("currentBless", Info.currentBless).apply();
                    while(true) {
                        b = rnd.nextInt(5);
                        if (Info.mediaPlayer[b] != null) {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Info.mediaPlayer[b].setOnCompletionListener(mp -> {Info.bibibibBisnesmanVozvrashenie=true;mp.stop();});
                                    Info.mediaPlayer[b].setVolume(Info.volume / 100f, Info.volume / 100f);
                                    Info.mediaPlayer[b].start();
                                }
                            }, Info.delay * 1000);
                            break;                        }
                    }}
        }
        );

        recorder.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (recorder != null) {
            recorder.stop();
            recorder = null;
        }

        if (poiskChihania != null) {
            poiskChihania.close();
            poiskChihania = null;
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("sneeze_channel", "Детектор чиха", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}