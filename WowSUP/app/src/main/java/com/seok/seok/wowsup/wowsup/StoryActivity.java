package com.seok.seok.wowsup.wowsup;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.dialog.BanConfirmDialog;
import com.seok.seok.wowsup.dialog.DeleteConfirmDialog;
import com.seok.seok.wowsup.dialog.FriendConfirmDialog;
import com.seok.seok.wowsup.fragments.fragprofile.ProfileFragment;
import com.seok.seok.wowsup.retrofit.model.ResponseStory;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {
    @BindView(R.id.story_txt_title)
    TextView txtTitle;
    @BindView(R.id.story_txt_body)
    TextView txtBody;
    @BindView(R.id.story_txt_tag1)
    TextView txtTag1;
    @BindView(R.id.story_txt_tag2)
    TextView txtTag2;
    @BindView(R.id.story_txt_tag3)
    TextView txtTag3;
    @BindView(R.id.story_txt_tag4)
    TextView txtTag4;
    @BindView(R.id.story_txt_tag5)
    TextView txtTag5;
    @BindView(R.id.story_txt_cntlike)
    TextView txtLike;
    @BindView(R.id.story_menu)
    BoomMenuButton boomMenuButton;
    @BindView(R.id.story_ibtn_like)
    ImageView iBtnLike;
    @BindView(R.id.story_layout_background)
    LinearLayout layoutBack;
    private String storyID, storyUserID, imageURL;
    private HamButton.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        setBoomMenu();
        initData();
    }

    @OnClick(R.id.story_layout_background)
    void showBack() {

    }

    @OnClick(R.id.story_ibtn_back)
    void goBack() {
        finish();
    }

    @OnClick(R.id.story_ibtn_like)
    void doLike() {
        ApiUtils.getStoryService().likeStory(GlobalWowSup.getInstance().getId(), storyID).enqueue(new Callback<ResponseStory>() {
            @Override
            public void onResponse(Call<ResponseStory> call, Response<ResponseStory> response) {
                if (response.isSuccessful()) {
                    ResponseStory body = response.body();
                    setBtnLike(body.getState());
                    txtLike.setText(body.getCntLike() + "");
                }
            }

            @Override
            public void onFailure(Call<ResponseStory> call, Throwable t) {

            }
        });
    }

    protected void initData() {
        Intent intent = getIntent();
        storyID = intent.getStringExtra("storyID");
            ApiUtils.getStoryService().pickStory(GlobalWowSup.getInstance().getId(), storyID).enqueue(new Callback<ResponseStory>() {
                @Override
                public void onResponse(Call<ResponseStory> call, Response<ResponseStory> response) {
                    if (response.isSuccessful()) {
                        ResponseStory body = response.body();
                        storyUserID = body.getUserID();
                        txtTitle.setText(body.getTitle());
                        txtLike.setText(body.getCntLike() + "");
                        txtBody.setText(body.getBody());
                        txtTag1.setText(body.getTag1());
                        txtTag2.setText(body.getTag2());
                        txtTag3.setText(body.getTag3());
                        txtTag4.setText(body.getTag4());
                        txtTag5.setText(body.getTag5());
                        setBtnLike(body.getState());
                        imageURL = body.getImageURL();
                        Glide.with(getApplicationContext())
                                .load(body.getImageURL()).into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                layoutBack.setBackground(resource);
                            }
                        });
                    }
                    if(storyUserID==null){
                        Toast.makeText(StoryActivity.this, "It has already been deleted.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseStory> call, Throwable t) {

                }
            });
        }


    protected void setBoomMenu() {
// 글을 삭제할지 친구요청을 보낼시, 글을 신고할지를 선택할수 있는 메뉴 빌드
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            builder = new HamButton.Builder()
                    .textSize(17)
                    .imagePadding(new Rect(30, 30, 30, 30))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (index == 0) {//친구신청
                                FriendConfirmDialog friendConfirmDialog = new FriendConfirmDialog(StoryActivity.this);
                                friendConfirmDialog.setTxtQnA(getString(R.string.ask_friend));
                                friendConfirmDialog.requestApplyFriend(GlobalWowSup.getInstance().getId(), storyUserID);
                                friendConfirmDialog.show();
                            } else if (index == 1) {//글삭제
                                DeleteConfirmDialog deleteConfirmDialog = new DeleteConfirmDialog(StoryActivity.this);
                                deleteConfirmDialog.setTxtQnA(getString(R.string.ask_delete));
                                if (deleteConfirmDialog.confirmStory(GlobalWowSup.getInstance().getId(), storyUserID, storyID))
                                    deleteConfirmDialog.show();
                                else
                                    Toast.makeText(StoryActivity.this, "Other 'SupPeople's writings.", Toast.LENGTH_LONG).show();
                            } else if (index == 2) {//글 신고하기
                                BanConfirmDialog banConfirmDialog = new BanConfirmDialog(StoryActivity.this);
                                banConfirmDialog.setTxtQnA(getString(R.string.ask_ban));
                                if(banConfirmDialog.confirmStory(GlobalWowSup.getInstance().getId(), storyUserID, storyID))
                                    banConfirmDialog.show();
                                else
                                    Toast.makeText(StoryActivity.this, "My post.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            if (i == 0) {
                builder.normalImageRes(R.drawable.send_icon)
                        .normalText("Friend request")
                        .subNormalText("Send a friend to SupPeople");
            } else if (i == 1) {
                builder.normalImageRes(R.drawable.delete)
                        .normalText("Delete Post")
                        .subNormalText("Delete my posts");
            } else if (i == 2) {
                builder.normalImageRes(R.drawable.ban_icon)
                        .normalText("To ban")
                        .subNormalText("Report this post");
            }
            boomMenuButton.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Top);
            boomMenuButton.addBuilder(builder);
        }
    }

    protected void setBtnLike(int state) {
        if (state == 0)
            iBtnLike.setImageResource(R.drawable.unllike_icon);
        else
            iBtnLike.setImageResource(R.drawable.like_icon);
    }
}
