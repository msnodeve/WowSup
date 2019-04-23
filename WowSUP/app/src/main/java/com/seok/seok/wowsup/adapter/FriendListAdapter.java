package com.seok.seok.wowsup.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.dialog.ChatOptionDialog;
import com.seok.seok.wowsup.dialog.FriendConfirmDialog;
import com.seok.seok.wowsup.retrofit.model.ResponseChat;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.wowsup.ChatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ChatViewHolder> {

    private View view;
    private List<ResponseChat> chatList;
    private Context context;

    public FriendListAdapter(Context context, List<ResponseChat> chatListObj){
        this.context = context;
        this.chatList = chatListObj;
    }

    //채팅 어댑터 생성 바인딩 그룹
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext().getApplicationContext()).inflate(R.layout.layout_chat_card, viewGroup, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder chatViewHolder, int i) {
        final ResponseChat item = chatList.get(i);
        Common.friendNick = item.getFriendNick();
        chatViewHolder.txtFID.setText(item.getFriendNick());
        chatViewHolder.txtFInfo.setText(item.getSelfish());
        Glide.with(context.getApplicationContext()).load(item.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(context.getApplicationContext())).into(chatViewHolder.imgProfile);
        chatViewHolder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatOptionDialog chatOptionDialog = new ChatOptionDialog(context, item.getFriend());
                chatOptionDialog.getWindow().setBackgroundDrawableResource(R.color.float_transparent);
                chatOptionDialog.show();
            }
        });
        chatViewHolder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendID = item.getFriend();
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("friendID", friendID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        //채팅 바인딩 뷰 홀더 클래스 리사이클러 뷰에 뿌리기위함
        @BindView(R.id.card_chat_txt_fid) TextView txtFID;
        @BindView(R.id.card_chat_txt_finfo) TextView txtFInfo;
        @BindView(R.id.card_chat_img_profile) ImageView imgProfile;
        @BindView(R.id.card_chat_btn_option) Button btnOption;
        public ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

