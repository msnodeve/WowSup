package com.seok.seok.wowsup.fragments.fragstory;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.adapter.CardListAdapter;
import com.seok.seok.wowsup.retrofit.model.ResponseProfile;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.CardData;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryFragment extends Fragment {
    private View view;
    private ArrayList<CardData> cardViewData;
    private CardListAdapter mAdapter;
    @BindView(R.id.frag_st_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.frag_st_txt_topic)
    TextView txtTopic;
    public StoryFragment() {

    }

    public static StoryFragment newInstance() {
        return new StoryFragment ();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewData = new ArrayList<>();
        mAdapter = new CardListAdapter(cardViewData, this.getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_story, container, false);
        ButterKnife.bind(this, view);
        init();
        initData();
        return view;
    }
    @OnClick(R.id.frag_st_btn_search) void searchHash(){

    }
    protected void init() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    protected void initData() {
        for (int i = 1; i <= 50; i++) {
            cardViewData.add(new CardData(i + "", i + "", i + "", i + "", i + "", "http://www.heywowsup.com/wowsup/Image/SupPeople_basic2.png"));
        }
        mRecyclerView.setAdapter(mAdapter);
    }
}
