package com.seok.seok.wowsup.fragments.fragprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.dialog.NoticeDialog;
import com.seok.seok.wowsup.wowsup.StoreActivity;
import com.seok.seok.wowsup.wowsup.StoryWriteActivity;
import com.seok.seok.wowsup.wowsup.SupPeopleInformationActivity;
import com.seok.seok.wowsup.retrofit.model.ResponseProfile;
import com.seok.seok.wowsup.retrofit.model.ResponseStory;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.CardData;
import com.seok.seok.wowsup.adapter.CardListAdapter;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private View view;
    private ArrayList<CardData> cardViewData;
    private CardListAdapter mAdapter;
    private int start;
    @BindView(R.id.frag_pf_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.frag_pf_txt_like)
    TextView txtLike;
    @BindView(R.id.frag_pf_txt_friend)
    TextView txtFriend;
    @BindView(R.id.frag_pf_txt_token)
    TextView txtToken;
    @BindView(R.id.frag_pf_ibtn_notice)
    Button btnNotice;
    @BindView(R.id.frag_pf_img_profile)
    ImageView imgProfile;

    public ProfileFragment() {
        cardViewData = new ArrayList<>();
        start = 0;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CardListAdapter(cardViewData, this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        init();
        getServerProfile();
        getDataFromServer();
        return view;
    }

    @OnClick(R.id.frag_pf_ibtn_write)
    void writeStory() {
        startActivity(new Intent(this.getContext(), StoryWriteActivity.class));
    }

    @OnClick(R.id.frag_pf_ibtn_notice)
    void noticeFriend() {
        ApiUtils.getProfileService().notice(GlobalWowSup.getInstance().getId()).enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                if(response.isSuccessful()){
                    ResponseProfile body = response.body();
                    if(body.getState()==0){
                        new NoticeDialog(getContext(), body.getCntNotice()).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {

            }
        });
        //startActivity(new Intent(getActivity().getApplication(), NoticeDialog.class));
    }

    @OnClick(R.id.frag_pf_img_profile)
    void updateProfile() {
        startActivity(new Intent(this.getContext(), SupPeopleInformationActivity.class));
    }

    @OnClick(R.id.frag_pf_lay_store)
    void goStore() {
        startActivity(new Intent(this.getContext(), StoreActivity.class));
    }

    protected void init() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    protected void getServerProfile() {
        ApiUtils.getProfileService().profile(GlobalWowSup.getInstance().getId()).enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                Log.d("WowSup_profile_HTTP", "http trans Success");
                if (response.isSuccessful()) {
                    ResponseProfile body = response.body();
                    txtLike.setText(body.getCntLike() + "");
                    txtFriend.setText(body.getCntFriend() + "");
                    txtToken.setText(body.getToken() + "");
                    btnNotice.setText(body.getCntNotice() + "");
                    Glide.with(getActivity()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).into(imgProfile);
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Log.d("WowSup_profile_HTTP", "http trans Failed");
            }
        });
    }

    public void getDataFromServer() {
        cardViewData.clear();
        ApiUtils.getStoryService().myStory(GlobalWowSup.getInstance().getId()).enqueue(new Callback<List<ResponseStory>>() {
            @Override
            public void onResponse(Call<List<ResponseStory>> call, Response<List<ResponseStory>> response) {
                if (response.isSuccessful()) {
                    Log.d("WowSup_Profile_Response", "http Response Success");
                    List<ResponseStory> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        cardViewData.add(new CardData(body.get(i).getStoryID() + "",
                                body.get(i).getUserID() + "", body.get(i).getTitle() + "",
                                body.get(i).getBody() + "", body.get(i).getCntLike() + "", body.get(i).getImageURL()));
                    }
                    if (mAdapter.getItemCount() == body.size()) {
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseStory>> call, Throwable t) {

            }
        });
    }
}