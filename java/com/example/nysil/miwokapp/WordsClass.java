package com.example.nysil.miwokapp;


public class WordsClass {

    private String miwokWord;
    private String englishWord;
    private int imageView = NO_IMAGE_PROVIDED;
    private int audioResource;

    private static final int NO_IMAGE_PROVIDED=-1;

    WordsClass(String miwokWord, String englishWord, int imageView, int audioResourse)
    {
        this.miwokWord=miwokWord;
        this.englishWord=englishWord;
        this.imageView=imageView;
        this.audioResource=audioResourse;
    }

    WordsClass(String miwokWord, String englishWord, int audioResourse)
    {
        this.miwokWord=miwokWord;
        this.englishWord=englishWord;
        this.audioResource=audioResourse;
    }

    public String returnMiwok()
    {
        return miwokWord;
    }

    public String returnEnglish()
    {
        return englishWord;
    }

    public int returnImage()
    {
        return imageView;
    }

    public boolean hasImage()
    {
        return imageView != NO_IMAGE_PROVIDED;
    }

    public int getAudio()
    {
        return audioResource;
    }
}
