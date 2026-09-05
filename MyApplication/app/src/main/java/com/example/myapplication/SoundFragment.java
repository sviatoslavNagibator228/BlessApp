package com.example.myapplication;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.DetecTorBogGroma.Info;
import com.example.myapplication.databinding.FragmentSoundBinding;


public class SoundFragment extends Fragment {
    private FragmentSoundBinding binding;
    private Uri urik= null;
    TextView txt;
    int sishik2dote0SuperVersion;
    int indexPlayera;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSoundBinding.inflate(inflater, container, false);

        txt = binding.txt;
        if(!Info.hranilishe.getString(String.valueOf(Info.sishik), "-1").equals("-1")) {
            try{
            txt.setText(getFileName(Uri.parse( Info.hranilishe.getString(String.valueOf(Info.sishik), " "))));
            }
            catch(Exception e){}
        }
        sishik2dote0SuperVersion=Info.sishik;
        Info.sishik++;
        if(Info.sishik == 5){
            Info.sishik = 0;
        }

        ImageButton ubrat = binding.ubrat;
        ImageButton suda = binding.suda;
        suda.setOnClickListener(v -> {
            audioPicker.launch(new String[]{"audio/*"});
        });
        ubrat.setOnClickListener(v -> {
            urik = null;
            txt.setText("");
            if(indexPlayera != -1){
            Info.editor.putString(String.valueOf(sishik2dote0SuperVersion), null).apply();
            Info.mediaPlayer[indexPlayera] = null;}
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private final ActivityResultLauncher<String[]> audioPicker = registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
        if (uri != null) {
            urik = uri;
            indexPlayera = -1;
            for (int i = 0; i < Info.mediaPlayer.length; i++) {
                if (Info.mediaPlayer[i] == null) {
                    requireContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    txt.setText (getFileName(uri));
                    indexPlayera = i;
                    Info.mediaPlayer[indexPlayera] = MediaPlayer.create(getContext(), urik);
                    Info.editor.putString(String.valueOf(sishik2dote0SuperVersion), urik.toString()).apply();
                    break;
                }
            }
        }
    });
    private String getFileName(Uri uri) {
        String result=null;
        Cursor cursor = requireActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index != -1) {
                    result = cursor.getString(index);
                }
            }
            cursor.close();
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}