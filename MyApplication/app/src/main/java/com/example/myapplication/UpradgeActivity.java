package com.example.myapplication;

import android.content.Intent;

import com.example.myapplication.DetecTorBogGroma.Info;
import com.yandex.mobile.ads.common.AdError;
import com.yandex.mobile.ads.common.AdRequest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityUpradgeBinding;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader;


public class UpradgeActivity extends AppCompatActivity {
    ActivityUpradgeBinding binding;
    private View animation;
    private boolean pokazalLi = true;
    @Nullable
    InterstitialAdLoader mInterstitialAdLoader = null;
    @Nullable
    InterstitialAd mInterstitialAd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUpradgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        Info.init(this);

        if(Info.schotchikKilov <4){Info.schotchikKilov++;startActivity(new Intent(UpradgeActivity.this, MainActivity.class));}
        ImageView im1 = binding.imageButton;
        ImageView im3 = binding.imageButton3;
        animation = binding.krutoAnimation;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.coconut1, new SoundFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.coconut2, new SoundFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.coconut3, new SoundFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.coconut4, new SoundFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.coconut5, new SoundFragment()).commit();
        }

        TextView txtMaxNumber = binding.txtMax;
        TextView txtRecover = binding.cur;
        txtMaxNumber.setText(Info.maxNumberOfBless/5 + "/3");
        txtRecover.setText("current:" + Info.currentBless);

        ImageButton home = binding.imageButton;
        home.setOnClickListener(goHome->{
            Info.slave();
            animation.animate().translationX(-im1.getX()).setDuration(300).start();
            startActivity(new Intent(UpradgeActivity.this, MainActivity.class));
        });
        ImageButton settings = binding.imageButton3;
        settings.setOnClickListener(goSettings->{
            Info.slave(); 
            animation.animate().translationX(im3.getX()).setDuration(300).start();
            startActivity(new Intent(UpradgeActivity.this, SettingsActivity.class));
        });
        mInterstitialAdLoader = new InterstitialAdLoader(this);
        loadInterstitialAd();

        ImageButton increase = binding.increaseMaxNumberOfBless;
        increase.setOnClickListener(increaseMaxNumberOfBless ->{
            if(Info.maxNumberOfBless < 15){
            showAd();
            if(pokazalLi == true){
                Info.maxNumberOfBless += 5;
                txtMaxNumber.setText(Info.maxNumberOfBless/5 + "/3");
            }
            }
        });
        ImageButton recover = binding.recoverBless;
        recover.setOnClickListener(recoverBless ->{
            if(Info.currentBless < Info.maxNumberOfBless){
                showAd();
            if(pokazalLi == true ){
                Info.currentBless = Info.maxNumberOfBless;
                txtRecover.setText("current:" + Info.currentBless);
            }
            }
        });
    }
    private void loadInterstitialAd() {
        if (mInterstitialAdLoader != null) {
            final AdRequest adRequest = new AdRequest.Builder("demo-interstitial-yandex").build();
            mInterstitialAdLoader.loadAd(adRequest, new InterstitialAdLoadListener() {
                @Override
                public void onAdLoaded(@NonNull final InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                    System.out.println("loh");
                }
            });
        }
    }
    private void showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdEventListener(new InterstitialAdEventListener() {
                @Override
                public void onAdShown() {
                pokazalLi =true;
                }

                @Override
                public void onAdFailedToShow(@NonNull final AdError adError) {
                    Toast.makeText((UpradgeActivity.this), "Oops, something went wrong", Toast.LENGTH_SHORT).show();
                    pokazalLi = false;
                }

                @Override
                public void onAdDismissed() {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.setAdEventListener(null);
                        mInterstitialAd = null;
                    }
                    loadInterstitialAd();
                }

                @Override
                public void onAdClicked() {
                }

                @Override
                public void onAdImpression(@Nullable final ImpressionData impressionData) {
                }
            });
            mInterstitialAd.show(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInterstitialAdLoader = null;
        destroyInterstitialAd();
    }

    private void destroyInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdEventListener(null);
            mInterstitialAd = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Info.slave();

    }
}
