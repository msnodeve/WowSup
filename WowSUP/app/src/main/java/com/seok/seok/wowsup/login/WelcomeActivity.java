package com.seok.seok.wowsup.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.utilities.ChatData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("message");
        databaseReference.push().setValue(new ChatData("a", "asdf"));
        //databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @OnClick(R.id.welcome_btn_login) void btnLogin(){
        //startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
//        ChatData chatData = new ChatData("a", "msndo!?!?");  // 유저 이름과 메세지로 chatData 만들기
//        databaseReference.child("message").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
    }
    @OnClick(R.id.welcome_btn_signup) void btnSignUp(){
        //startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
        mAuth.createUserWithEmailAndPassword("msno@msno.com", "msnomsno")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("asdf",user.getEmail());
                            Log.d("asdf",user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                        }

                        // ...
                    }
                });

    }
}
