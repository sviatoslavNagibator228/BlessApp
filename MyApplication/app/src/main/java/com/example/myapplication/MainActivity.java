package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.DetecTorBogGroma.Info;
import com.example.myapplication.databinding.ActivityMainBinding;

import android.os.Handler;

import java.time.LocalTime;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private View animation;
    ActivityMainBinding binding;
    private Locale locale;
    GradientDrawable shapi;
    GradientDrawable shapi1 ;
    GradientDrawable shapi2 ;
    GradientDrawable shapi3 ;
    GradientDrawable shapi4;
    GradientDrawable shapi5 ;
    GradientDrawable shapi6 ;
    GradientDrawable shapi7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        Info.init(this);
        if (!Info.hranilishe.contains("volume")) {
            Info.editor.putInt("volume", Info.volume).apply();
            Info.editor.putInt("currentBless", Info.currentBless).apply();
            Info.editor.putInt("maxNumberOfBless", Info.maxNumberOfBless).apply();
            Info.editor.putInt("index", Info.index).apply();
            Info.editor.putInt("delay", Info.delay).apply();
            Info.editor.putString("am", Info.am).apply();
            Info.editor.putString("pm", Info.pm).apply();
            Info.editor.putString("edt1", Info.edt1).apply();
            Info.editor.putString("edt2", Info.edt2).apply();
            Info.editor.putString("edt3", Info.edt3).apply();
            Info.editor.putString("edt4", Info.edt4).apply();
            Info.editor.putBoolean("is", Info.is).apply();
        }
            Info.edt1 = Info.hranilishe.getString("edt1", "#10367d");
            Info.edt2 = Info.hranilishe.getString("edt2", "#5710367d");
            Info.edt3 = Info.hranilishe.getString("edt3", "#73b3d9");
            Info.edt4 = Info.hranilishe.getString("edt4", "#FFFFFF");
            shapi = (GradientDrawable) getDrawable(R.drawable.shapes);
            shapi1 = (GradientDrawable) getDrawable(R.drawable.shapes1);
            shapi2 = (GradientDrawable) getDrawable(R.drawable.shapes2);
            shapi3 = (GradientDrawable) getDrawable(R.drawable.shapes3);
            shapi4 = (GradientDrawable) getDrawable(R.drawable.shapes4);
            shapi5 = (GradientDrawable) getDrawable(R.drawable.shapes5);
            shapi6 = (GradientDrawable) getDrawable(R.drawable.shapes6);
            shapi7 = (GradientDrawable) getDrawable(R.drawable.shapes8);
            BitmapDrawable phone = (BitmapDrawable) getDrawable(R.drawable.phone);
            phone.setColorFilter(new PorterDuffColorFilter(Color.parseColor(Info.edt3), PorterDuff.Mode.SRC_IN));
            shapi.setColor(Color.parseColor(Info.edt1));
            shapi1.setColor(Color.parseColor(Info.edt3));
            shapi2.setColor(Color.parseColor(Info.edt1));
            shapi3.setColor(Color.parseColor(Info.edt3));
            shapi4.setColor(Color.parseColor(Info.edt1));
            shapi5.setColor(Color.parseColor(Info.edt2));
            shapi6.setColor(Color.parseColor(Info.edt2));
            shapi7.setColor(Color.parseColor(Info.edt4));
        if(Info.schotchikKilov<4){Info.schotchikKilov++;startActivity(new Intent(MainActivity.this, UpradgeActivity.class));}
        Info.is = Info.hranilishe.getBoolean("is", false);
        Info.currentBless = Info.hranilishe.getInt("currentBless", 5);
        Info.maxNumberOfBless = Info.hranilishe.getInt("maxNumberOfBless", 5);
        Info.index =Info.hranilishe.getInt("index", 1);
        Info.delay =Info.hranilishe.getInt("delay", 0);
        Info.volume =Info.hranilishe.getInt("volume", 50);
        Info.am = Info.hranilishe.getString("am","00:01");
        Info.pm= Info.hranilishe.getString("pm","11:59");
        for (int i = 0; i < Info.mediaPlayer.length; i++) {
            if(Info.hranilishe.contains(String.valueOf(i)))
                Info.mediaPlayer[i] = MediaPlayer.create(MainActivity.this, Uri.parse(Info.hranilishe.getString(String.valueOf(i), "1")));
        }

        if(Info.index==1){
            locale = new Locale("en");
        }
        else if(Info.index==2){
            locale = new Locale("es");
        }
        else if(Info.index==3){
            locale = new Locale("fr");
        }
        else if(Info.index==4){
            locale = new Locale("ru");
        }
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
        setTitle(R.string.app_name);

        TextView cucu = binding.cucucu;
        ProgressBar progressBar = binding.progressBar;
        progressBar.setMax(Info.maxNumberOfBless);
        progressBar.setProgress(Info.currentBless);
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                cucu.setText(Info.currentBless + "/" + Info.maxNumberOfBless);
                progressBar.setProgress(Info.currentBless);
                handler.postDelayed(this, 100);
            }
        };
        handler.post(updater);

        ImageView im2 = binding.imageButton2;
        ImageView im3 = binding.imageButton3;
        animation = binding.krutoAnimation;
        requestMicrophonePermission();

        Intent intent1 = new Intent(this, SneezeDetection.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent1);}
        else {
            startService(intent1);}

        Button onOff = binding.onOff;
        if(Info.is == false){
            onOff.setText(getResources().getString(R.string.off));}
        else if(Info.is == true){
            onOff.setText(getResources().getString(R.string.on));}
        onOff.setOnClickListener(onto-> {
            if(Info.is == false) {
                Info.is = true;
                onOff.setText(getResources().getString(R.string.on));
            }
            else if(Info.is == true){
                onOff.setText(getResources().getString(R.string.off));
                Info.is = false;
            }
        });

        ImageButton exit = binding.ArrowButton;
        exit.setOnClickListener(quit->{
            finishAffinity();
            System.exit(0);
        });
        ImageButton share = binding.ShareButton;
        share.setOnClickListener(shareApp->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String body = "Download this super-mega-puper cool app!";
            String sub = "http://play.google.com";
            intent.putExtra(Intent.EXTRA_TEXT, body);
            intent.putExtra(Intent.EXTRA_TEXT, sub);
            startActivity(Intent.createChooser(intent, "Share using"));
        });
        ImageButton upradge = binding.imageButton2;
        upradge.setOnClickListener(goUpradge->{
            animation.animate().translationX(im2.getX()).setDuration(300).start();
            startActivity(new Intent(MainActivity.this, UpradgeActivity.class));
        });
        ImageButton settings = binding.imageButton3;
        settings.setOnClickListener(goSettings->{
            animation.animate().translationX(im3.getX()).setDuration(600).start();
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });
    }
    private void requestMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 100);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Info.slave();

    }

}