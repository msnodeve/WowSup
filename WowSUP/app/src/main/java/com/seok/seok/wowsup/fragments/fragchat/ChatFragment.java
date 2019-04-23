package com.seok.seok.wowsup.fragments.fragchat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.adapter.FriendListAdapter;
import com.seok.seok.wowsup.retrofit.model.ResponseChat;
import com.seok.seok.wowsup.retrofit.model.ResponseProfile;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowSup;
import com.seok.seok.wowsup.wowsup.SupPeopleInformationActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//        ChatData chatData = new ChatData("a", "msndo!?!?");  // 유저 이름과 메세지로 chatData 만들기
//        databaseReference.child("message").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
public class ChatFragment extends Fragment {
    private View view;

    @BindView(R.id.frag_chat_list)
    RecyclerView recyclerView;
    @BindView(R.id.frag_chat_img_profile)
    ImageView imgProfile;
    @BindView(R.id.frag_chat_lay_banner)
    LinearLayout layoutBanner;
    @BindView(R.id.frag_chat_txt_id)
    TextView txtID;
    @BindView(R.id.frag_chat_txt_info)
    TextView txtInfo;

    public ChatFragment() {

    }

    public static ChatFragment newInstance() {
        return new ChatFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        init();
        getMyProfile();
        getFriendList();
        return view;

    }
    @OnClick(R.id.frag_chat_img_profile) void updateProfile(){
        startActivity(new Intent(this.getContext(), SupPeopleInformationActivity.class));
    }
    protected void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }
    protected void getMyProfile(){
        ApiUtils.getProfileService().profile(GlobalWowSup.getInstance().getId()).enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                Log.d("WowSup_chat_HTTP", "http trans Success");
                if (response.isSuccessful()) {
                    ResponseProfile body = response.body();
                    layoutBanner.setBackgroundColor(Common.WOWSUP_COLOR[body.getBanner()]);
                    txtID.setText(GlobalWowSup.getInstance().getId());
                    txtInfo.setText(body.getSelfish());
                    Glide.with(getActivity()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getActivity())).into(imgProfile);
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Log.d("WowSup_chat_HTTP", "http trans Failed");
            }
        });
    }
    protected void getFriendList(){
        ApiUtils.getChatService().getFriendList(GlobalWowSup.getInstance().getId()).enqueue(new Callback<List<ResponseChat>>() {
            @Override
            public void onResponse(Call<List<ResponseChat>> call, Response<List<ResponseChat>> response) {
                Log.d("WowSup_chat_HTTP", "http trans Success");
                if(response.isSuccessful()){
                    Log.d("WowSup_chat_RESPONSE", "http response Success");
                    List<ResponseChat> body = response.body();
                    FriendListAdapter friendListAdapter = new FriendListAdapter(getActivity(), body);
                    recyclerView.setAdapter(friendListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseChat>> call, Throwable t) {
                Log.d("WowSup_chat_HTTP", "http trans Failed");
            }
        });
    }
}