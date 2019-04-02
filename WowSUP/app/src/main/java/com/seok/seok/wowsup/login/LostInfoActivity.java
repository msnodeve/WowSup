package com.seok.seok.wowsup.login;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseMail;
import com.seok.seok.wowsup.retrofit.remote.ApiMailUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostInfoActivity extends AppCompatActivity {
    @BindView(R.id.lostinfo_edit_id_email) EditText edtIDEmail;
    @BindView(R.id.lostinfo_edit_pw_id) EditText edtPWID;
    @BindView(R.id.lostinfo_edit_pw_email) EditText edtPWEmail;
    @BindView(R.id.lostinfo_layout_id) LinearLayout layoutID;
    @BindView(R.id.lostinfo_layout_pw) LinearLayout layoutPW;
    @BindView(R.id.lostinfo_layout_id_title) LinearLayout layoutIDTitle;
    @BindView(R.id.lostinfo_layout_pw_title) LinearLayout layoutPWTitle;
    @BindView(R.id.lostinfo_ibtn_back) ImageView iBtnBack;
    @BindView(R.id.lostinfo_ibtn_id) ImageView iBtnID;
    @BindView(R.id.lostinfo_ibtn_pw) ImageView iBtnPW;

    private Callback retrofitCallBack;
    private CountDownTimer countDownTimer;
    private long emailSendCount;
    private boolean checkID, checkPW, sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_info);
        ButterKnife.bind(this);
        init();
    }

    @OnClick(R.id.lostinfo_btn_id) void lostID() {
        if (!sendMail) {
            ApiMailUtils.getEmailService().findID(edtIDEmail.getText().toString()).enqueue(retrofitCallBack);
            countDownTimer();
        } else {
            Toast.makeText(LostInfoActivity.this, "You can do it again in " + emailSendCount + " seconds.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.lostinfo_btn_pw) void lostPW() {
        if (!sendMail) {
            ApiMailUtils.getEmailService().findPW(edtPWID.getText().toString(), edtPWEmail.getText().toString()).enqueue(retrofitCallBack);
            countDownTimer();
        } else {
            Toast.makeText(LostInfoActivity.this, "You can do it again in " + emailSendCount + " seconds.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.lostinfo_ibtn_id) void viewID() {
        layoutPW.setVisibility(View.GONE);
        layoutPWTitle.setBackgroundResource(R.drawable.id_tab_block);
        iBtnPW.setImageResource(R.drawable.tab_down_button);
        if (!checkID) {
            layoutID.setVisibility(View.VISIBLE);
            layoutIDTitle.setBackgroundResource(R.drawable.find_id_block);
            iBtnID.setImageResource(R.drawable.tab_up_button);
            checkID = true;
        } else {
            layoutID.setVisibility(View.GONE);
            layoutIDTitle.setBackgroundResource(R.drawable.id_tab_block);
            iBtnID.setImageResource(R.drawable.tab_down_button);
            checkID = false;
        }
        checkPW = false;
    }

    @OnClick(R.id.lostinfo_ibtn_pw) void viewPW() {
        layoutID.setVisibility(View.GONE);
        layoutIDTitle.setBackgroundResource(R.drawable.id_tab_block);
        iBtnID.setImageResource(R.drawable.tab_down_button);
        if (!checkPW) {
            layoutPW.setVisibility(View.VISIBLE);
            layoutPWTitle.setBackgroundResource(R.drawable.find_password_block);
            iBtnPW.setImageResource(R.drawable.tab_up_button);
            checkPW = true;
        } else {
            layoutPW.setVisibility(View.GONE);
            layoutPWTitle.setBackgroundResource(R.drawable.id_tab_block);
            iBtnPW.setImageResource(R.drawable.tab_down_button);
            checkPW = false;
        }
        checkID = false;
    }

    public void init() {
        retrofitCallBack = new Callback<ResponseMail>() {
            @Override
            public void onResponse(Call<ResponseMail> call, Response<ResponseMail> response) {
                Log.d("WowSup_LostInfo_HTTP", "http trans Success");
                if (response.isSuccessful()) {
                    ResponseMail body = response.body();
                    if (body.getState() == 0) {
                        Toast.makeText(LostInfoActivity.this, "Email Send!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LostInfoActivity.this, "Email not Send!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMail> call, Throwable t) {
                Log.d("WowSup_LostInfo_HTTP", "http trans Failed");
                Toast.makeText(LostInfoActivity.this, "Email not Send!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void countDownTimer() {
        sendMail = true;
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                emailSendCount = millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                sendMail = false;
                countDownTimer.cancel();
            }
        }.start();
    }
}
