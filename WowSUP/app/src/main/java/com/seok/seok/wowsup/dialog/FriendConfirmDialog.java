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
import com.seok.seok.wowsup.retrofit.model.ResponseCommon;
import com.seok.seok.wowsup.retrofit.model.ResponseStory;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendConfirmDialog extends Dialog {
    @BindView(R.id.dialog_layout)
    LinearLayout layout;
    @BindView(R.id.dialog_txt_qna)
    TextView txtQnA;
    private Context context;
    private Callback retrofitCallBack;
    private String strQnA, userID, otherUserID;

    public FriendConfirmDialog(Context context) {
        super(context);
        this.context = context;
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
        retrofitCallBack = new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                Log.d("WowSup_FriendDialog_HTTP", "http trans Success");
                if (response.isSuccessful()) {
                    ResponseCommon body = response.body();
                    if (body.getState() == 0) {
                        Toast.makeText(context, "Friend request success!", Toast.LENGTH_LONG).show();
                    } else if (body.getState() == 2) {
                        Toast.makeText(context, "You are already a friend request.", Toast.LENGTH_LONG).show();
                    }else if(body.getState() == 1){
                        Toast.makeText(context, "It is my own writing.", Toast.LENGTH_LONG).show();
                    }
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                Log.d("WowSup_FriendDialog_HTTP", "http trans Failed");
            }
        };
    }

    @OnClick(R.id.dialog_btn_yes)
    void confirmYes() {
        ApiUtils.getCommonService().applyFriend(GlobalWowSup.getInstance().getId(), otherUserID).enqueue(retrofitCallBack);
    }

    @OnClick(R.id.dialog_btn_no)
    void confirmNo() {
        dismiss();
    }

    public void requestApplyFriend(String userID, String otherUserID) {
        this.userID = userID;
        this.otherUserID = otherUserID;
    }

    public void setTxtQnA(String strQnA) {
        this.strQnA = strQnA;
    }
}
