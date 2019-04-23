package com.seok.seok.wowsup.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.utilities.ChatData;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    private List<ChatData> mChat;
    private String strEmail;

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v;
        if (i == 1) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_right_card, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_left_card, viewGroup, false);
        }

        ChatViewHolder vh = new ChatViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder chatViewHolder, int i) {
        chatViewHolder.mTextView.setText(mChat.get(i).getText());
    }

    public ChatListAdapter(List<ChatData> mChat, String email) {
        this.mChat = mChat;
        this.strEmail = email;
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mChat.get(position).getEmail().equals(strEmail)) {
            return 1;
        } else {
            return 2;
        }
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ChatViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.mTextView);
        }
    }
}
