package com.example.nysil.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK|| focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange== AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if (focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordsClass> words = new ArrayList<>();
        words.add(new WordsClass("epe", "Father", R.drawable.family_father, R.raw.family_father));
        words.add(new WordsClass("eta", "Mother", R.drawable.family_mother, R.raw.family_mother));
        words.add(new WordsClass("angsi", "Son", R.drawable.family_son, R.raw.family_son));
        words.add(new WordsClass("tune", "Daughter", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new WordsClass("taachi", "Older Brother", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new WordsClass("chalitti", "Younger Brother", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new WordsClass("tete", "Older Sister", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new WordsClass("kolliti", "Younger Sister", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new WordsClass("ama", "Grandmother", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new WordsClass("paapa", "Grandfather", R.drawable.family_grandfather, R.raw.family_grandfather));

        CustomArrayAdapter arrayAdapter=new CustomArrayAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.familyRootView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WordsClass word= words.get(position);
                releaseMediaPlayer();

                int result= audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudio());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }
    private void releaseMediaPlayer()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer=null;

            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        releaseMediaPlayer();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        releaseMediaPlayer();
    }
}
