package com.seok.seok.wowsup.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seok.seok.wowsup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.welcome_btn_login) void btnLogin(){
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
    }
    @OnClick(R.id.welcome_btn_signup) void btnSignUp(){
        startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
    }
}
