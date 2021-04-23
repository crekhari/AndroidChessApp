package com.example.androidchess52;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
    }

    public void onClick(View view) {
        ImageButton selectedPiece = (ImageButton) view;
        String location = view.getResources().getResourceName(selectedPiece.getId());
        System.out.println(location);
    }

}
