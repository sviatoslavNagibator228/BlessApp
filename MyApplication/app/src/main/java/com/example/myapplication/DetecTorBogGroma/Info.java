package com.example.myapplication.DetecTorBogGroma;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Info {
    public static int schotchikKilov=0;
    public static String edt1 = "#10367d";
    public static String edt2 = "#5710367d";
    public static String edt3 = "#73b3d9";
    public static String edt4 = "#FFFFFF";
    public static double a;
    public static String am = "00:01";
    public static String pm = "11:59";
    public static int maxNumberOfBless = 5;
    public static int currentBless = 5;
    public static int index = 1;
    public static int volume = 50;
    public static boolean bibibibBisnesmanVozvrashenie= true;
    public static int delay = 0;
    public static boolean is = false;
    public static MediaPlayer[] mediaPlayer = new MediaPlayer[5];
    public static int sishik;
    public static SharedPreferences hranilishe;
    public static SharedPreferences.Editor editor;
    public static void init(Context context) {
        hranilishe = context.getSharedPreferences("S", Context.MODE_PRIVATE);
        editor = hranilishe.edit();}
    public static void slave(){
        Info.hranilishe.edit().putInt("volume", Info.volume).apply();
        Info.hranilishe.edit().putInt("currentBless", Info.currentBless).apply();
        Info.hranilishe.edit().putInt("maxNumberOfBless", Info.maxNumberOfBless).apply();
        Info.hranilishe.edit().putInt("index", Info.index).apply();
        Info.hranilishe.edit().putInt("delay", Info.delay).apply();
        Info.hranilishe.edit().putString("am", Info.am).apply();
        Info.hranilishe.edit().putString("pm", Info.pm).apply();
        Info.hranilishe.edit().putString("edt1", Info.edt1).apply();
        Info.hranilishe.edit().putString("edt2", Info.edt2).apply();
        Info.hranilishe.edit().putString("edt3", Info.edt3).apply();
        Info.hranilishe.edit().putString("edt4", Info.edt4).apply();
        Info.hranilishe.edit().putBoolean("is", Info.is).apply();
    }
}

