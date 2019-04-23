package com.seok.seok.wowsup.wowsup;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.adapter.ChatListAdapter;
import com.seok.seok.wowsup.utilities.ChatData;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.chat_txt_friend)
    TextView txtFriend;
    @BindView(R.id.chat_list)
    RecyclerView chatList;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<ChatData> chatDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        init();
    }
    @OnClick(R.id.chat_ibtn_help) void goHelp(){

    }
    @OnClick(R.id.chat_ibtn_back) void goBack(){

    }
    @OnClick(R.id.chat_btn_send) void send(){

    }
    protected void init(){
        chatDataList = new ArrayList<>();
        adapter = new ChatListAdapter(chatDataList, GlobalWowSup.getInstance().getUserEmail());
        chatList.setHasFixedSize(true);
        chatList.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatDataList.add(chatData);
                chatList.scrollToPosition(chatDataList.size() - 1);
                chatList.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
