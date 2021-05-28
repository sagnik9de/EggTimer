package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
   TextView textView;
   boolean counterActive=false;
   Button controller;
   CountDownTimer countDownTimer;
    MediaPlayer mplayer=new MediaPlayer();
    AudioManager audioManager;

    public void update(int secsleft){
        int mins=(int)secsleft/60;
        int secs=secsleft-(mins * 60);
        String secString=Integer.toString(secs);
         if(secs<=9){
            secString="0"+secString;
        }
        textView.setText(Integer.toString(mins)+":"+secString);
    }
    public void reset(){
        textView.setText("0:30");//resets back to 30 secs
        seekBar.setProgress(30);
        countDownTimer.cancel();
        controller.setText("GO!");
        seekBar.setEnabled(true);//changed back to true part
        counterActive=false;
    }
    public void controlTimer(View view){
        if(counterActive==false) {
            counterActive = true;
            seekBar.setEnabled(false);//this will disable the seekbar
            controller.setText("STOP!");
            countDownTimer=new CountDownTimer(seekBar.getProgress() * 1000 + 150, 1000) {//here +100 is used to reduce the delay of the countdown

                @Override
                public void onTick(long millisUntilFinished) {
                    //this method will have all the update when the button is clicked
                    update((int) millisUntilFinished / 1000);//type casting from long to integer
                    //to change it from milliseconds to secs

                }

                @Override
                public void onFinish() {
                    reset();//resets back to every place on finishing the countdown
                    Log.i("finished", "timer done");
                     mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);
                    mplayer.start();




                }
            }.start();
        }else{//if the stop button is encountered then counterActive will be true and everything will be reset
                reset();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textview);
        controller=(Button)findViewById(R.id.controller);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        seekBar.setMax(600);
        seekBar.setProgress(30);//this is set the progress from 30 secs .countdown will start from this time

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 //this to access the text view and change it when we scroll the seek bar
                update(progress);//set progress is 30 hence the argument of the update function will be 30 and thus the time will start from 30 secs
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
