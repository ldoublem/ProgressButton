package com.ldoublem.progressButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ldoublem.progressButton.R;
import com.ldoublem.progressButton.view.ProgressButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressButton pb_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb_button=(ProgressButton)findViewById(R.id.pb_btn);
        pb_button.setBgColor(Color.RED);
        pb_button.setTextColor(Color.WHITE);
        pb_button.setProColor(Color.WHITE);
        pb_button.setButtonText("Login in");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pb_btn:
                pb_button.startAnim();
                Message m=mHandler.obtainMessage();
                mHandler.sendMessageDelayed(m,1500);
                break;
            default:
                break;
        }
    }


    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            pb_button.stopAnim(new ProgressButton.OnStopAnim() {
                @Override
                public void Stop() {
                    Intent i=new Intent();
                    i.setClass(MainActivity.this,SecondActivity.class);
                    startActivity(i);
                }
            });

        }
    };





}
