package com.seok.seok.wowsup.fragments.fragstory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.adapter.CardListAdapter;
import com.seok.seok.wowsup.retrofit.model.ResponseStory;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.CardData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryFragment extends Fragment {
    private View view;
    private ArrayList<CardData> cardViewData;
    private int start, itemTotalCount;
    private boolean flag;
    private CardListAdapter mAdapter;
    @BindView(R.id.frag_st_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.frag_st_txt_topic)
    TextView txtTopic;

    public StoryFragment() {
        cardViewData = new ArrayList<>();
        start = 0;
        flag = false;
    }

    public static StoryFragment newInstance() {
        return new StoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CardListAdapter(cardViewData, this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        ButterKnife.bind(this, view);
        init();
        initData();
        if (start == 0) {
            getDataFromServer();
        }
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @OnClick(R.id.frag_st_btn_search)
    void searchHash() {
    }

    protected void init() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(onScrollChangeListener);
    }

    RecyclerView.OnScrollListener onScrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItemPos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            itemTotalCount = recyclerView.getAdapter().getItemCount();
            Log.d("asdfasdf", lastVisibleItemPos + "  " + itemTotalCount);
            if ((lastVisibleItemPos >= itemTotalCount - 2) && !flag) {
                if((start % 20) == 0) {
                    flag = true;
                    getDataFromServer();
                }else{
                    Toast.makeText(getContext(), "No more stories to import.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    protected void initData() {
        ApiUtils.getStoryService().recommendTag(new Random().nextInt(5)).enqueue(new Callback<ResponseStory>() {
            @Override
            public void onResponse(Call<ResponseStory> call, Response<ResponseStory> response) {
                if (response.isSuccessful()) {
                    ResponseStory body = response.body();
                    if (body.getState() == 0) {
                        txtTopic.setText(body.getTag());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseStory> call, Throwable t) {

            }
        });
    }

    protected void getDataFromServer() {
        ApiUtils.getStoryService().showStory(start + 1).enqueue(new Callback<List<ResponseStory>>() {
            @Override
            public void onResponse(Call<List<ResponseStory>> call, Response<List<ResponseStory>> response) {
                Log.d("WowSup_Story_HTTP", "http trans Success");
                if (response.isSuccessful()) {
                    Log.d("WowSup_Story_Response", "http Response Success");
                    List<ResponseStory> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        cardViewData.add(new CardData(body.get(i).getStoryID() + "",
                                body.get(i).getUserID() + "", body.get(i).getTitle() + "",
                                body.get(i).getBody() + "", body.get(i).getCntLike() + "", body.get(i).getImageURL()));
                    }
                    start += body.size();
                    if (mAdapter.getItemCount() == start) {
                        mRecyclerView.setAdapter(mAdapter);
                        if(start!=20)
                            mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 14);
                        flag = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseStory>> call, Throwable t) {
                Log.d("WowSup_Story_HTTP", "http trans Failed");
            }
        });
    }
}
