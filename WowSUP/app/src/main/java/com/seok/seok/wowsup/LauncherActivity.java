package com.seok.seok.wowsup;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seok.seok.wowsup.login.LoginActivity;
import com.seok.seok.wowsup.login.WelcomeActivity;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        GlobalWowSup.getInstance().setScreenSize(getWindowManager().getDefaultDisplay());
        mHandler.sendEmptyMessageDelayed(0, 100);
    }
    // App Logo 시각화 1.5초 뒤 LoginActivity 전환
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(LauncherActivity.this, WelcomeActivity.class));
            finish();
        }
    };
}
