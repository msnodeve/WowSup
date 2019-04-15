package com.seok.seok.wowsup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseStory;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanConfirmDialog extends Dialog {
    @BindView(R.id.dialog_layout)
    LinearLayout layout;
    @BindView(R.id.dialog_txt_qna)
    TextView txtQnA;
    private Context context;
    private String strQnA, userID, storyID;

    public BanConfirmDialog(Context context) {
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
    }

    @OnClick(R.id.dialog_btn_yes)
    void confirmYes() {
        ApiUtils.getStoryService().banStory(GlobalWowSup.getInstance().getId(),storyID).enqueue(new Callback<ResponseStory>() {
            @Override
            public void onResponse(Call<ResponseStory> call, Response<ResponseStory> response) {
                if (response.isSuccessful()) {
                    ResponseStory body = response.body();
                    if(body.getState() == 0){
                        Toast.makeText(context, "The post has been Reported! Thank you!", Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                    }
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<ResponseStory> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.dialog_btn_no)
    void confirmNo() {
        dismiss();
    }

    //내가 쓴 글이 아닌 경우 판단
    public boolean confirmStory(String userID, String otherUserID, String storyID) {
        if (!userID.equals(otherUserID)) {
            this.userID = userID;
            this.storyID = storyID;
            return true;
        } else
            return false;
    }

    public void setTxtQnA(String strQnA) {
        this.strQnA = strQnA;
    }
}
