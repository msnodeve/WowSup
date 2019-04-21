package com.seok.seok.wowsup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseChat;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatConfirmDialog extends Dialog {
    @BindView(R.id.dialogn_layout)
    LinearLayout layout;
    @BindView(R.id.dialogn_edt_nick)
    EditText edtNick;
    @BindView(R.id.dialogn_txt_qna)
    TextView txtQnA;
    private Context context;
    private String friendID;
    private int themeResId;
    private Callback retrofitCallback;
    public ChatConfirmDialog(Context context, int themeResId, String friendID) {
        super(context, themeResId);
        this.context = context;
        this.themeResId = themeResId;
        this.friendID = friendID;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_nick_confirm);
        ButterKnife.bind(this);
        setUI();
        retrofitCallback = new Callback<ResponseChat>() {
            @Override
            public void onResponse(Call<ResponseChat> call, Response<ResponseChat> response) {
                if(response.isSuccessful()){
                    ResponseChat body = response.body();
                    if(body.getState()==0){
                        Toast.makeText(context, body.getMsg(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }else{
                        Toast.makeText(context, body.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseChat> call, Throwable t) {
                dismiss();
            }
        };
    }
    @OnClick(R.id.dialogn_btn_yes)
    void confirmYes(){
        switch (themeResId){
            case 0:
                ApiUtils.getChatService().setUpdateNick(GlobalWowSup.getInstance().getId(), friendID, edtNick.getText().toString()).enqueue(retrofitCallback);
                break;
            case 1:
                ApiUtils.getChatService().deleteFriend(GlobalWowSup.getInstance().getId(), friendID).enqueue(retrofitCallback);
                break;
        }
    }
    @OnClick(R.id.dialogn_btn_no)
    void confirmNo() {
        dismiss();
    }
    public void setUI(){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(layout.getLayoutParams());
        layoutParams.width = (int) (GlobalWowSup.getInstance().getUserWidth() / 1.3);
        layoutParams.height = (int) (GlobalWowSup.getInstance().getUserHeight() / 3.5);
        layoutParams.gravity = Gravity.CENTER;
        layout.setLayoutParams(layoutParams);
        switch (themeResId){
            case 0:        //닉네임 변경을 눌렀을 경우
                edtNick.setVisibility(View.VISIBLE);
                txtQnA.setVisibility(View.GONE);
                break;
            case 1:        //채팅 나가기를 눌렀을 경우
                edtNick.setVisibility(View.GONE);
                txtQnA.setVisibility(View.VISIBLE);
                txtQnA.setText(R.string.delete_chat);
                break;
        }
    }
}
