package com.seok.seok.wowsup.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.dialog.NoticeDialog;
import com.seok.seok.wowsup.retrofit.model.ResponseChat;
import com.seok.seok.wowsup.retrofit.model.ResponseCommon;
import com.seok.seok.wowsup.retrofit.model.ResponseFriend;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;
import com.seok.seok.wowsup.utilities.NoticeData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>{
    private View view;
    private ArrayList<NoticeData> items;
    private Context context;
    private Dialog dialog;

    public NoticeAdapter(Context context, ArrayList<NoticeData> items, Dialog dialog){
        this.context = context;
        this.items = items;
        this.dialog = dialog;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext().getApplicationContext()).inflate(R.layout.layout_notice_card, viewGroup, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder noticeViewHolder, int i) {
        final NoticeData item = items.get(i);
        noticeViewHolder.txtFriendNick.setText(item.getApplyer());
        noticeViewHolder.txtFriendInfo.setText(item.getUserSelfish());
        noticeViewHolder.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        noticeViewHolder.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getCommonService().confirmFriend(GlobalWowSup.getInstance().getId(), item.getApplyer()).enqueue(new Callback<ResponseCommon>() {
                    @Override
                    public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                        if(response.isSuccessful()){
                            ResponseCommon body = response.body();
                            if(body.getState()==0){
                                Toast.makeText(context, "Accept success", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Accept failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ResponseCommon> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        //바인딩 뷰 홀더 클래스 리사이클러 뷰에 뿌리기위함
        @BindView(R.id.card_notice_btn_yes) Button btnYes;
        @BindView(R.id.card_notice_btn_no) Button btnNo;
        @BindView(R.id.card_notice_txt_finfo) TextView txtFriendInfo;
        @BindView(R.id.card_notice_txt_fnick) TextView txtFriendNick;
        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
