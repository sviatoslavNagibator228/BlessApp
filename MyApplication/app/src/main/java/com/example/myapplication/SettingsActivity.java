package com.example.myapplication;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DetecTorBogGroma.Info;
import com.example.myapplication.databinding.SettingsBinding;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.Locale;


public class SettingsActivity extends AppCompatActivity {
    SettingsBinding binding;
    private Locale locale;
    private View animation;
    EditText am;
    EditText pm;
    private String lastText = "";
    private int countOfCommas= 0;
    private GradientDrawable shape ;
    private GradientDrawable shape2 ;
    private GradientDrawable shape3 ;
    private GradientDrawable shape4 ;
    private GradientDrawable shape5;
    private GradientDrawable shape6 ;
    private GradientDrawable shape1 ;
    private GradientDrawable shape8;
    private BitmapDrawable phone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = SettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        Info.init(this);

        ImageView im1 = binding.imageButton;
        ImageView im2 = binding.imageButton2;
        animation = binding.krutoAnimation;
        EditText edt1 = binding.ed1;
        EditText edt2 = binding.ed2;
        EditText edt3 = binding.ed3;
        EditText edt4 = binding.ed4;
        String edt11 = "#10367d";
        String edt22 = "#5710367d";
        String edt33 = "#73b3d9";
        String edt44 = "#FFFFFF";
        shape = (GradientDrawable) getDrawable(R.drawable.shapes);
        shape2 = (GradientDrawable) getDrawable(R.drawable.shapes2);
        shape3 = (GradientDrawable) getDrawable(R.drawable.shapes3);
        shape4 = (GradientDrawable) getDrawable(R.drawable.shapes4);
        shape5 = (GradientDrawable) getDrawable(R.drawable.shapes5);
        shape6 = (GradientDrawable) getDrawable(R.drawable.shapes6);
        shape1 = (GradientDrawable) getDrawable(R.drawable.shapes1);
        shape8 = (GradientDrawable) getDrawable(R.drawable.shapes8);
        phone = (BitmapDrawable) getDrawable(R.drawable.phone);
       com.skydoves.colorpickerview.ColorPickerView picker1 = binding.colorPicker;
       com.skydoves.colorpickerview.ColorPickerView picker2= binding.colorPicker1;
        com.skydoves.colorpickerview.ColorPickerView picker3= binding.colorPicker2;
       com.skydoves.colorpickerview.ColorPickerView picker4= binding.colorPicker3;
       tsvetnoyNosok(picker1, edt1,edt11, shape, shape2, shape4, null);
       tsvetnoyNosok(picker2, edt2,edt22, shape5, shape6, null, null);
       tsvetnoyNosok(picker3, edt3,edt33,  shape3, shape1, null, phone);
       tsvetnoyNosok(picker4, edt4,edt44, shape8, null, null, null);

        ImageButton upradge = binding.imageButton;

        upradge.setOnClickListener(goUpradge->{
            Info.slave();
            animation.animate().translationX(-im1.getX()).setDuration(600).start();
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        });
        ImageButton settings = binding.imageButton2;
        settings.setOnClickListener(goSettings->{
            Info.slave();
            animation.animate().translationX(-im2.getX()).setDuration(300).start();
            startActivity(new Intent(SettingsActivity.this, UpradgeActivity.class));
        });

        TextView del = binding.delay;
        del.setText(String.valueOf(Info.delay));
        ImageButton bolshe = binding.bolshe;
        bolshe.setOnClickListener(uvel->{
            if (Info.delay < 9) {
            Info.delay++;
            del.setText(Integer.toString(Info.delay));
            }
        });
        ImageButton menshe = binding.menshe;
        menshe.setOnClickListener(umen->{
            if(Info.delay > 0) {
                Info.delay--;
                del.setText(Integer.toString(Info.delay));
            }});

        ImageButton sledushi = binding.sledushi;
        ImageButton proshli = binding.proshli;
        sledushi.setOnClickListener(next->{
            Info.index++;
            if(Info.index > 4){
                Info.index = 1;
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
            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
        });
        proshli.setOnClickListener(back->{
            Info.index--;
            if(Info.index < 1){
                Info.index = 4;
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
            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
        });

        am = binding.am;
        pm = binding.pm;
        am.setText(Info.am.substring(0,5));
        pm.setText(Info.pm.substring(0,5));
        am.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                int new_countOfCommas = s.toString().length() - s.toString().replace(":", "").length();
                if(new_countOfCommas < countOfCommas) {
                    int cursor_pos = am.getSelectionStart();
                    am.setText(lastText);
                    if(cursor_pos <= lastText.length()) {
                        am.setSelection(cursor_pos);
                    }
                } else {
                    countOfCommas = s.toString().length() - s.toString().replace(":", "").length();
                    lastText = s.toString();
                }
                Info.am = am.getText().toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        pm.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                int new_countOfCommas = s.toString().length() - s.toString().replace(":", "").length();
                if(new_countOfCommas < countOfCommas) {
                    int cursor_pos = pm.getSelectionStart();
                    pm.setText(lastText);
                    if(cursor_pos <= lastText.length()) {
                        pm.setSelection(cursor_pos);
                    }
                } else {
                    countOfCommas = s.toString().length() - s.toString().replace(":", "").length();
                    lastText = s.toString();
                }
                Info.pm = pm.getText().toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        SeekBar seekBar = binding.seekBar;
        seekBar.setProgress(Info.volume);
        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,int progress, boolean fromUser) {
                Info.volume = progress;
                }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }
    void tsvetnoyNosok(com.skydoves.colorpickerview.ColorPickerView picker, EditText edt, String text, GradientDrawable shapes, GradientDrawable shapes2, GradientDrawable shapes3, BitmapDrawable map){
        if (text.equals("#10367d")) {
            shape.setColor(Color.parseColor(Info.edt1));
            shape2.setColor(Color.parseColor(Info.edt1));
            shape4.setColor(Color.parseColor(Info.edt1));
            edt.setBackgroundColor(Color.parseColor(Info. edt1));
            edt.setText(Info.edt1);
        }
        else if (text.equals("#5710367d")) {
            shape5.setColor(Color.parseColor(Info.edt2));
            shape6.setColor(Color.parseColor(Info.edt2));
            edt.setBackgroundColor(Color.parseColor(Info.edt2));
            edt.setText(Info.edt2);
        }
        else if (text.equals("#73b3d9")) {
            shape3.setColor(Color.parseColor(Info.edt3));
            shape1.setColor(Color.parseColor(Info.edt3));
            phone.setColorFilter(new PorterDuffColorFilter(Color.parseColor( Info.edt3), PorterDuff.Mode.SRC_IN));
            edt.setBackgroundColor(Color.parseColor(Info.edt3));
            edt.setText(Info.edt3);
        }
        else if ( text.equals("#FFFFFF")) {
            shape8.setColor(Color.parseColor(Info.edt4));
            edt.setBackgroundColor(Color.parseColor(Info.edt3));
            edt.setText(Info.edt4);
        }

        picker.setColorListener(new ColorEnvelopeListener() {

            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                String hex = "#" + envelope.getHexCode();
                edt.setText(hex);
                edt.setBackgroundColor(Color.parseColor(hex));
            }
        });
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String color=s.toString();
                    if (!color.startsWith("#")) {
                        color = "#" + color;
                    }
                    Color.parseColor(color);
                    edt.setBackgroundColor(Color.parseColor(color));
                    if(!edt.getText().toString().equals("#FFFFFFFF") && !edt.getText().toString().equals("#FFFEFFFE")){
                    if (shapes != null) {
                        shapes.setColor(Color.parseColor(edt.getText().toString()));
                    }
                    if (shapes2 != null) {
                        shapes2.setColor(Color.parseColor(edt.getText().toString()));}
                    if (shapes3 != null) {
                        shapes3.setColor(Color.parseColor(edt.getText().toString()));}
                    if(map!=null){
                        map.setColorFilter(new PorterDuffColorFilter(Color.parseColor(edt.getText().toString()), PorterDuff.Mode.SRC_IN));
                    }
                    if (text.equals("#10367d")) {
                        Info.edt1 = edt.getText().toString();
                    }
                    else if (text.equals("#5710367d")) {
                        Info.edt2 = edt.getText().toString();
                    }
                    else if (text.equals("#73b3d9")) {
                        Info.edt3 = edt.getText().toString();
                    }
                    else if ( text.equals("#FFFFFF")) {
                        Info.edt4 = edt.getText().toString();
                    }}
                } catch (Exception ignored) {
                }
            }
        });
        edt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String currentText = edt.getText().toString();
                try {
                    String color = edt.getText().toString();
                    Color.parseColor(color);

                } catch (Exception e) {
                    edt.setText(text);
                    edt.setBackgroundColor(Color.parseColor(text));
                    if (shapes != null) {
                        shapes.setColor(Color.parseColor(edt.getText().toString()));}
                    if (shapes2 != null) {
                        shapes2.setColor(Color.parseColor(edt.getText().toString()));}
                    if (shapes3 != null) {
                        shapes3.setColor(Color.parseColor(edt.getText().toString()));}
                    if(map!=null){
                        map.setColorFilter(new PorterDuffColorFilter(Color.parseColor(edt.getText().toString()), PorterDuff.Mode.SRC_IN));
                    }
                    if (text.equals("#10367d")) {
                        Info.edt1 = edt.getText().toString();
                    }
                    else if (text.equals("#5710367d")) {
                        Info.edt2 = edt.getText().toString();
                    }
                    else if (text.equals("#73b3d9")) {
                        Info.edt3 = edt.getText().toString();
                    }
                    else if ( text.equals("#FFFFFF")) {
                        Info.edt4 = edt.getText().toString();
                    }
                 }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        Info.slave();
    }
}