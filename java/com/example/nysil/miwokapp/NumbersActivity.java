package com.example.nysil.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
              mediaPlayer.pause();
              mediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
              mediaPlayer.start();
            }
            else if(focusChange== AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }

        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordsClass> words = new ArrayList<>();
        words.add(new WordsClass("lutti", "One", R.drawable.number_one, R.raw.number_one));
        words.add(new WordsClass("otlko", "Two", R.drawable.number_two, R.raw.number_two));
        words.add(new WordsClass("tolookosu", "Three", R.drawable.number_three, R.raw.number_three));
        words.add(new WordsClass("oyylsa", "Four", R.drawable.number_four, R.raw.number_four));
        words.add(new WordsClass("massokka", "Five", R.drawable.number_five, R.raw.number_five));
        words.add(new WordsClass("temmokka", "Six", R.drawable.number_six, R.raw.number_six));
        words.add(new WordsClass("kenekaku", "Seven", R.drawable.number_seven, R.raw.number_seven));
        words.add(new WordsClass("kawinta", "Eight", R.drawable.number_eight, R.raw.number_eight));
        words.add(new WordsClass("wo'e", "Nine", R.drawable.number_nine, R.raw.number_nine));
        words.add(new WordsClass("na'aacha", "Ten", R.drawable.number_ten, R.raw.number_ten));

        CustomArrayAdapter arrayAdapter=new CustomArrayAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.listNumbers);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WordsClass word= words.get(position);

                releaseMediaPlayer();


                int result= audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudio());
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

            mediaPlayer =null;

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
