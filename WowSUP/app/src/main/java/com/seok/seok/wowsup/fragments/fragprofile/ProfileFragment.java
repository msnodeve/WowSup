package com.seok.seok.wowsup.fragments.fragprofile;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseProfile;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.CardData;
import com.seok.seok.wowsup.adapter.CardListAdapter;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.util.ArrayList;

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

    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewData = new ArrayList<>();
        mAdapter = new CardListAdapter(cardViewData, this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        init();
        getServerProfile();
        initData();

        return view;
    }

    @OnClick(R.id.frag_pf_ibtn_write)
    void writeStory() {

    }

    @OnClick(R.id.frag_pf_ibtn_notice)
    void noticeFriend() {

    }

    @OnClick(R.id.frag_pf_img_profile)
    void updateProfile() {

    }

    @OnClick(R.id.frag_pf_lay_store)
    void goStore() {

    }

    protected void init() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    protected void getServerProfile(){
        ApiUtils.getProfileService().profile(GlobalWowSup.getInstance().getId()).enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                Log.d("WowSup_profile_HTTP", "http trans Success");
                if(response.isSuccessful()){
                    ResponseProfile body = response.body();
                    txtLike.setText(body.getCntLike()+"");
                    txtFriend.setText(body.getCntFriend() + "");
                    txtToken.setText(body.getToken()+"");
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

    protected void initData() {



        for (int i = 1; i <= 16; i++) {
            cardViewData.add(new CardData(i + "", i + "", i + "", i + "", i + "", "http://www.heywowsup.com/wowsup/Image/SupPeople_basic2.png"));
        }
        mRecyclerView.setAdapter(mAdapter);
    }
}