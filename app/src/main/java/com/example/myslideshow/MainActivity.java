package com.example.myslideshow;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory{

    ImageSwitcher imageSwitcher;
    private int position = 0;
    private boolean isSlideshow = false;
    Handler handler = new Handler();
    MediaPlayer mediaPlayer;

    private int[] resources = {
            R.drawable.slide00, R.drawable.slide01,
            R.drawable.slide02, R.drawable.slide03,
            R.drawable.slide04, R.drawable.slide05,
            R.drawable.slide06, R.drawable.slide07,
            R.drawable.slide08, R.drawable.slide09,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageSwitcher = findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(this);
        imageSwitcher.setImageResource(resources[0]);

        Button prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setInAnimation(MainActivity.this, android.R.anim.fade_in);
                imageSwitcher.setOutAnimation(MainActivity.this, android.R.anim.fade_out);
                movePosition(-1);
            }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setOutAnimation(MainActivity.this, android.R.anim.slide_out_right);
                imageSwitcher.setInAnimation(MainActivity.this, android.R.anim.slide_in_left);
                movePosition(1);
            }
        });

        Button slideshowButton = findViewById(R.id.slideshowButton);
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSlideshow = !isSlideshow;
                if (isSlideshow){
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            }
        });

        final int period = 5000;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isSlideshow){
                    movePosition(1);
                }
                handler.postDelayed(this, period);
            }
        };
        handler.post(runnable);

        mediaPlayer = MediaPlayer.create(this, R.raw.noescape);
        mediaPlayer.setLooping(true);

    }

    private void movePosition(int move){
        position += move;
        if (position >= resources.length){
            position = 0;
        } else if (position < 0){
            position = resources.length - 1;
        }
        imageSwitcher.setImageResource(resources[position]);
    }

    public View makeView(){
        ImageView i = new ImageView(this);
        return i;
    }
}
