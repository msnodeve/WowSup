package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rey.material.widget.Slider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupPeopleInformationActivity extends AppCompatActivity {
    @BindView(R.id.info_slider)
    Slider slider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_people_information);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.info_btn_modify) void ge(){Toast.makeText(this, slider.getValue()+"", Toast.LENGTH_SHORT).show();}

}
