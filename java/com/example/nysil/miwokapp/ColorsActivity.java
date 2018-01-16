package com.example.nysil.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener()
    {
        @Override
                public void onAudioFocusChange(int focusChange)
        {
          if(focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT|| focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
          {
              mediaPlayer.pause();
              mediaPlayer.seekTo(0);
          }
          else if(focusChange== AudioManager.AUDIOFOCUS_GAIN)
          {
              mediaPlayer.start();
          }
          else if(focusChange== AudioManager.AUDIOFOCUS_LOSS)
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
        setContentView(R.layout.activity_colors);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordsClass> words = new ArrayList<>();
        words.add(new WordsClass("wetettl", "Black", R.drawable.color_black, R.raw.color_black));
        words.add(new WordsClass("chokokkl", "Brown", R.drawable.color_brown, R.raw.color_brown));
        words.add(new WordsClass("takaakkl", "Dusty Yellow",R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new WordsClass("topoppl", "Gray", R.drawable.color_gray , R.raw.color_gray));
        words.add(new WordsClass("kulull", "Green",R.drawable.color_green, R.raw.color_green ));
        words.add(new WordsClass("kelell", "Mustard Yellow", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        words.add(new WordsClass("topllse", "Red", R.drawable.color_red, R.raw.color_red));
        words.add(new WordsClass("chlwllte", "White", R.drawable.color_white, R.raw.color_white));

        CustomArrayAdapter arrayAdapter=new CustomArrayAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.colorsRootView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WordsClass word= words.get(position);

                releaseMediaPlayer();

                int result= audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudio());
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
    protected  void onStop()
    {
        super.onStop();

        releaseMediaPlayer();
    }
}
