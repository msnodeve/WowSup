package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoryWriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_write);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.write_ibtn_back) void back(){
        finish();
    }
    @OnClick(R.id.write_ibtn_help) void goHelp(){

    }
    @OnClick(R.id.write_ibtn_back1) void setBackground1(){

    }
    @OnClick(R.id.write_ibtn_back2) void setBackground2(){

    }
    @OnClick(R.id.write_ibtn_back3) void setBackground3(){

    }
    @OnClick(R.id.write_ibtn_back4) void setBackground4(){

    }
    @OnClick(R.id.write_ibtn_back5) void setBackground5(){

    }
    @OnClick(R.id.write_ibtn_picture) void setUserPicture(){

    }
    @OnClick(R.id.write_btn_save) void save(){

    }
}
