package com.seok.seok.wowsup.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.seok.seok.wowsup.R;

import java.util.ArrayList;

import butterknife.BindView;

public class NoticeActivity extends AppCompatActivity {
    @BindView(R.id.notice_layout_back)
    LinearLayout layout;
    @BindView(R.id.notice_list)
    RecyclerView recyclerView;

    private NoticeAdapter adapter;
    private ArrayList<NoticeData> cardData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
    }
}
