package com.seok.seok.wowsup.wowsup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.adapter.ChatListAdapter;
import com.seok.seok.wowsup.adapter.FriendListAdapter;
import com.seok.seok.wowsup.dialog.TranslateDialog;
import com.seok.seok.wowsup.retrofit.model.ResponseChat;
import com.seok.seok.wowsup.retrofit.model.ResponseProfile;
import com.seok.seok.wowsup.retrofit.model.ResponseWord;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.ChatData;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.chat_txt_friend)
    TextView txtFriend;
    @BindView(R.id.chat_list)
    RecyclerView chatList;
    private String friendID, delimiter;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<ChatData> chatDataList;
    private Map<String, String> wordMap;
    private int wordCount;
    public static EditText edtChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        wordMap = new HashMap<>();
        wordCount = 0;
        delimiter = " ";
        ButterKnife.bind(this);
        init();
    }

    @OnClick(R.id.chat_ibtn_help)
    void goHelp() {
        Common.translateOption = 3;
        new TranslateDialog(this).show();
    }

    @OnClick(R.id.chat_ibtn_back)
    void goBack() {
        finish();
    }

    @OnClick(R.id.chat_btn_send)
    void send() {
        //채팅에 어떤 언어를 쳤는지 데이터베이스에 넣기위한 알고리즘
        String edtText =  edtChat.getText().toString();
        boolean result = edtText.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
        if(edtText.equals("") || edtText.isEmpty()){
            Toast.makeText(ChatActivity.this, "Enter your chat.", Toast.LENGTH_SHORT).show();
        }else{
            if(result){
                Toast.makeText(ChatActivity.this, "Contains non-English characters.", Toast.LENGTH_LONG).show();
            }else{
                sentenceSend(edtText);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = simpleDateFormat.format(calendar.getTime());
                //파이어베이스 연동
                DatabaseReference meRef = firebaseDatabase.getReference("user").child(GlobalWowSup.getInstance().getId()).child("friend").child(friendID).child("chat").child(formattedDate);
                DatabaseReference friendRef = firebaseDatabase.getReference("user").child(friendID).child("friend").child(GlobalWowSup.getInstance().getId()).child("chat").child(formattedDate);
                Hashtable<String, String> chat = new Hashtable<>();
                //이메일과 텍스트를 넘김
                chat.put("date", formattedDate);
                chat.put("email", GlobalWowSup.getInstance().getUserEmail());
                chat.put("text", edtText);

                meRef.setValue(chat);
                friendRef.setValue(chat);

                edtChat.setText("");
            }
        }
    }

    protected void init() {
        edtChat = findViewById(R.id.chat_edt_chat);
        txtFriend.setText(Common.friendNick);
        Intent intent = getIntent();
        friendID = intent.getStringExtra("friendID");
        chatDataList = new ArrayList<>();
        adapter = new ChatListAdapter(chatDataList, GlobalWowSup.getInstance().getUserEmail());
        chatList.setHasFixedSize(true);
        chatList.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user").child(GlobalWowSup.getInstance().getId()).child("friend").child(friendID).child("chat");
        databaseReference.addChildEventListener(new ChildEventListener() {
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
    public void sentenceSend(String sentence) {
        //a-Z 까지 알파벳이아닌경우 잘라내어 서버에 등록
        String[] words = sentence.replaceAll("[^a-zA-Z]", " ").split(delimiter);
        for (String word : words) {
            if (!word.equals("")) {
                wordMap.put((wordCount++) + "", word);
            }
        }
        // 서버등록 통신
        ApiUtils.getWordService().requestChatWord(wordMap).enqueue(new Callback<ResponseWord>() {
            @Override
            public void onResponse(Call<ResponseWord> call, Response<ResponseWord> response) {
                Log.d("mapTrans", "map trans success");
            }

            @Override
            public void onFailure(Call<ResponseWord> call, Throwable t) {
                Log.d("mapTrans", t.getMessage());
            }
        });
        wordCount = 0;
        wordMap.clear();
    }
}
