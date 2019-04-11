package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rey.material.widget.Slider;
import com.seok.seok.wowsup.adapter.CountryAdapter;
import com.seok.seok.wowsup.retrofit.model.ResponseProfile;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.Country;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupPeopleInformationActivity extends AppCompatActivity {
    @BindView(R.id.info_img_profile)
    ImageView imgProfile;
    @BindView(R.id.info_txt_userid)
    TextView userID;
    @BindView(R.id.info_edt_info)
    EditText userSelf;
    @BindView(R.id.info_slider)
    Slider slider;
    @BindView(R.id.info_txt_age)
    TextView txtAge;
    @BindView(R.id.info_spinner)
    Spinner spinner;
    @BindView(R.id.info_group)
    RadioGroup genderGroup;
    private CountryAdapter spinnerAdapter;
    private int userAge, userBannerColor, change;
    private String userCountry, userGender;
    private LinearLayout[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_people_information);
        ButterKnife.bind(this);
        init();
        initData();
    }
    @OnClick(R.id.info_ibtn_back)
    void goBack() {
        finish();
    }
    @OnClick(R.id.info_btn_modify)
    void modify() {
        if(change==0){

        }else{

        }
    }

    public void initData(){
        userID.setText(GlobalWowSup.getInstance().getId());
        txtAge.setText("Age : " + slider.getValue());
        spinnerAdapter = new CountryAdapter(this, Common.countryArrayList);
        spinner.setAdapter(spinnerAdapter);
        slider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                txtAge.setText("Age : " + slider.getValue());
                userAge = slider.getValue();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country country = (Country) spinnerAdapter.getItem(position);
                userCountry = country.getCountry();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(genderGroup != null){
            genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    userGender = (R.id.info_radio_m == checkedId) ? "Male" : "Female";
                }
            });
        }
        ApiUtils.getProfileService().profile(GlobalWowSup.getInstance().getId()).enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                Log.d("WowSup_Info_HTTP", "http trans Success");
                if(response.isSuccessful()){
                    ResponseProfile body = response.body();
                    if(body.getState()==0) {
                        Glide.with(getApplicationContext()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(imgProfile);
                        userSelf.setText(body.getSelfish());
                        slider.setValue(body.getAge(),true);
                        txtAge.setText("Age : " + body.getAge());
                        change = body.getChange();
                        setGroup(body.getGender());
                        setCountry(body.getNationality());
                        layouts[body.getBanner()].setBackgroundResource(Common.PICK_BANNER[body.getBanner()]);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Log.d("WowSup_Info_HTTP", "http trans Failed");
            }
        });
    }
    public void setCountry(String country){
        for(int i = 0 ; i<Common.countryArrayList.size(); i++){
            if(Common.countryArrayList.get(i).getCountry().equals(country))
                spinner.setSelection(i);
        }
    }
    public void setGroup(String gender){
        if(gender.equals("Male")){
            genderGroup.check(R.id.info_radio_m);
        }else{
            genderGroup.check(R.id.info_radio_f);
        }
    }
    public void init() {
        layouts = new LinearLayout[10];
        layouts[0] = findViewById(R.id.info_layout_set1);
        layouts[1] = findViewById(R.id.info_layout_set2);
        layouts[2] = findViewById(R.id.info_layout_set3);
        layouts[3] = findViewById(R.id.info_layout_set4);
        layouts[4] = findViewById(R.id.info_layout_set5);
        layouts[5] = findViewById(R.id.info_layout_set6);
        layouts[6] = findViewById(R.id.info_layout_set7);
        layouts[7] = findViewById(R.id.info_layout_set8);
        layouts[8] = findViewById(R.id.info_layout_set9);
        layouts[9] = findViewById(R.id.info_layout_set10);
        for (int i = 0; i < layouts.length; i++) {
            layouts[i].setOnClickListener(onClickListener);
        }
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layouts[userBannerColor].setBackgroundColor(Common.WOWSUP_COLOR[userBannerColor]);
            switch (v.getId()) {
                case R.id.info_layout_set1:
                    userBannerColor = 0;
                    break;
                case R.id.info_layout_set2:
                    userBannerColor = 1;
                    break;
                case R.id.info_layout_set3:
                    userBannerColor = 2;
                    break;
                case R.id.info_layout_set4:
                    userBannerColor = 3;
                    break;
                case R.id.info_layout_set5:
                    userBannerColor = 4;
                    break;
                case R.id.info_layout_set6:
                    userBannerColor = 5;
                    break;
                case R.id.info_layout_set7:
                    userBannerColor = 6;
                    break;
                case R.id.info_layout_set8:
                    userBannerColor = 7;
                    break;
                case R.id.info_layout_set9:
                    userBannerColor = 8;
                    break;
                case R.id.info_layout_set10:
                    userBannerColor = 9;
                    break;
            }
            layouts[userBannerColor].setBackgroundResource(Common.PICK_BANNER[userBannerColor]);
        }
    };
}
