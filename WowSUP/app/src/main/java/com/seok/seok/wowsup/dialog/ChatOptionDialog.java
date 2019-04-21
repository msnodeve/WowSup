package com.seok.seok.wowsup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.seok.seok.wowsup.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatOptionDialog extends Dialog {
    private Context context;
    private String friendID;
    public ChatOptionDialog( Context context, String friendID) {
        super(context);
        this.context = context;
        this.friendID = friendID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_friend_option);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog_btn_nick) void setFriendNick(){
        new ChatConfirmDialog(context,0, friendID).show();
    }
    @OnClick(R.id.dialog_btn_out) void removeFriend(){
        new ChatConfirmDialog(context,1, friendID).show();
    }
}
