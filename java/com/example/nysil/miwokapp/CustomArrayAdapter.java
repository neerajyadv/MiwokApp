package com.example.nysil.miwokapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class CustomArrayAdapter extends ArrayAdapter<WordsClass> {
    public CustomArrayAdapter(@NonNull Context context, ArrayList<WordsClass> words ) {
        super(context, 0, words);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        WordsClass wordsClass=getItem(position);

        View listView=convertView;
        if(  listView == null)
        {
            listView= LayoutInflater.from(getContext()).inflate( R.layout.listview_layout , parent, false);
        }


        ImageView imageView=(ImageView)listView.findViewById(R.id.imageView_Layout);
        if (wordsClass.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(wordsClass.returnImage());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(GONE);
        }

        TextView textView1=(TextView)listView.findViewById(R.id.textView_Miwok);
        textView1.setText(wordsClass.returnMiwok());

        TextView textView2=(TextView)listView.findViewById(R.id.textView_English);
        textView2.setText(wordsClass.returnEnglish());

      return listView;
    }
}
