package com.seok.seok.wowsup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseProfile;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyConfirmDialog extends Dialog {
    @BindView(R.id.dialog_layout)
    LinearLayout layout;
    @BindView(R.id.dialog_txt_qna)
    TextView txtQnA;
    private Context context;
    private Callback retrofitCallBack;
    private String strQnA, userInfo, gender, country;
    private int userAge, banner, change;

    public ModifyConfirmDialog(Context context) {
        super(context);
        this.context = context;
    }
    @OnClick(R.id.dialog_btn_yes)
    void confirmYes() {
        updateProfile();
    }

    @OnClick(R.id.dialog_btn_no)
    void confirmNo() {
        dismiss();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        ButterKnife.bind(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(layout.getLayoutParams());
        layoutParams.width = (int) (GlobalWowSup.getInstance().getUserWidth() / 1.3);
        layoutParams.height = (int) (GlobalWowSup.getInstance().getUserHeight() / 3.5);
        layout.setLayoutParams(layoutParams);
        txtQnA.setText(strQnA);
    }
    public void setData(String userInfo, int userAge, String gender, String country, int banner, int change){
        this.userInfo = userInfo;
        this.userAge = userAge;
        this.gender = gender;
        this.country = country;
        this.banner = banner;
        this.change = change;
    }
    public void setTxtQnA(String strQnA) {
        this.strQnA = strQnA;
    }
    public void updateProfile(){
        ApiUtils.getProfileService().updateProfile(GlobalWowSup.getInstance().getId(),userAge,gender,country,userInfo, banner, change).enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                if(response.isSuccessful()){
                    ResponseProfile body = response.body();
                    if(body.getState()==0){
                        Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    }else {
                        Toast.makeText(context, "Update Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Log.d("WowSup_ModifyDialog_HTTP", "http trans Failed");
            }
        });
    }
}
