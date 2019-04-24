package com.seok.seok.wowsup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.adapter.NoticeAdapter;
import com.seok.seok.wowsup.retrofit.model.ResponseFriend;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;
import com.seok.seok.wowsup.utilities.NoticeData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeDialog extends Dialog {
    @BindView(R.id.notice_layout_back)
    LinearLayout layout;
    @BindView(R.id.notice_list)
    RecyclerView recyclerView;

    private NoticeAdapter adapter;
    private ArrayList<NoticeData> cardData;
    private Context context;
    private int cntNotice;

    public NoticeDialog(@NonNull Context context, int cntNotice) {
        super(context);
        this.context = context;
        this.cntNotice = cntNotice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);

        init();
        initData();

    }

    protected void initData() {
        if (cntNotice == 0) {
            layout.setBackgroundResource(R.drawable.no_alarms_arrived);
        } else {
            ApiUtils.getProfileService().noticeData(GlobalWowSup.getInstance().getId()).enqueue(new Callback<List<ResponseFriend>>() {
                @Override
                public void onResponse(Call<List<ResponseFriend>> call, Response<List<ResponseFriend>> response) {
                    if (response.isSuccessful()) {
                        List<ResponseFriend> body = response.body();
                        for (int i = 0; i < body.size(); i++) {
                            Log.d("asdf", body.get(i).getApplyer());
                            Log.d("asdf", body.get(i).getUserSelfish());
                            cardData.add(new NoticeData(body.get(i).getApplyer(), body.get(i).getUserSelfish()));
                        }
                        if (adapter.getItemCount() == body.size()) {
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseFriend>> call, Throwable t) {

                }
            });
        }
        recyclerView.setAdapter(adapter);
    }

    protected void init() {
        cardData = new ArrayList<>();
        adapter = new NoticeAdapter(context, cardData, this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(layout.getLayoutParams());
        layoutParams.width = (int) (GlobalWowSup.getInstance().getUserWidth() / 1.2);
        layoutParams.height = (int) (GlobalWowSup.getInstance().getUserHeight() / 1.5);
        layoutParams.gravity = Gravity.CENTER;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.scrollToPosition(0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layout.setLayoutParams(layoutParams);
    }
}
