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

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange== AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
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
        setContentView(R.layout.activity_phrases);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordsClass> words = new ArrayList<>();
        words.add(new WordsClass("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going));
        words.add(new WordsClass("tinnә oyaase'nә", "What is your name?", R.raw.phrase_what_is_your_name));
        words.add(new WordsClass("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        words.add(new WordsClass("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        words.add(new WordsClass("kuchi achit", "I'm feeling good.", R.raw.phrase_im_feeling_good));
        words.add(new WordsClass("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        words.add(new WordsClass("hәә’әәnәm", "Yes, I am coming.", R.raw.phrase_yes_im_coming));
        words.add(new WordsClass("әәnәm", "I'm coming.", R.raw.phrase_im_coming));
        words.add(new WordsClass("yoowutis", "Let's go.", R.raw.phrase_lets_go));
        words.add(new WordsClass("әnni'nem", "Come here.", R.raw.phrase_come_here));

        CustomArrayAdapter arrayAdapter=new CustomArrayAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.phrasesrootView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WordsClass word= words.get(position);
                releaseMediaPlayer();

                int result= audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudio());
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
