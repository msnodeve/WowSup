package com.seok.seok.wowsup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.utilities.CardData;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    private Context context;
    private View view;
    private ArrayList<CardData> items;
    public CardListAdapter(ArrayList<CardData> DataSet, Context context) {
        items = DataSet;
        this.context = context;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext().getApplicationContext()).inflate(R.layout.layout_story_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardListAdapter.ViewHolder viewHolder, int i) {
        CardData item = items.get(i);
        viewHolder.imgHeart.setImageResource(R.drawable.heart);
        viewHolder.txtTitle.setText(item.getTitle());
        viewHolder.txtLike.setText(item.getCntLike()+"");
        FrameLayout.LayoutParams backLayout = new FrameLayout.LayoutParams(viewHolder.layoutStoryBackground.getLayoutParams());
        backLayout.height = (int) (GlobalWowSup.getInstance().getUserHeight() / 5.5);
        backLayout.setMargins(10,10,10,10);
        viewHolder.layoutStoryBackground.setLayoutParams(backLayout);
        Glide.with(viewHolder.itemView.getContext().getApplicationContext())
                .load(item.getImageURL())
                .into(new ViewTarget<LinearLayout, GlideDrawable>((LinearLayout) viewHolder.itemView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        viewHolder.layoutStoryBackground.setBackground(resource);
                    }
                });
        //레이아웃 제목을 클릭할 경우 해당 storyID 값을 다음 엑티비티에 넘겨줌
        viewHolder.layoutStoryBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(view.getContext(), StoryActivity.class);
//                intent.putExtra("storyID", item.getStoryID());
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_story_txt_like) TextView txtLike;
        @BindView(R.id.card_story_title) TextView txtTitle;
        @BindView(R.id.card_story_img_like) ImageView imgHeart;
        @BindView(R.id.card_story_background) LinearLayout layoutStoryBackground;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
