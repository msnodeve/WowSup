package com.seok.seok.wowsup.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seok.seok.wowsup.MainActivity;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseLogin;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_edittext_id) EditText edtID;
    @BindView(R.id.login_edittext_pwd) EditText edtPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }
    // case Click Login
    @OnClick(R.id.login_btn_login) void wowsupLogin() {
        if(validateLogin(edtID.getText().toString(), edtPW.getText().toString())){
            ApiUtils.getUserService().doLogin(edtID.getText().toString(), edtPW.getText().toString()).enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    Log.d("WowSup_Login_HTTP", "http trans Success");
                    if(response.isSuccessful()){
                        Log.d("WowSup_Login_RESPONSE", "http response Success");
                        ResponseLogin body = response.body();
                        if(body.getState()==1){
                            GlobalWowSup.getInstance().setId(body.getUserID());
                            GlobalWowSup.getInstance().setUserEmail(body.getUserEmail());
                            GlobalWowSup.getInstance().setUserRegistryTime(body.getUserRegistryTime());
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Log.d("WowSup_Login_HTTP", "http trans Failed");
                }
            });
        }
    }
    // case Click Lost ID / PW
    @OnClick(R.id.login_btn_lost_idpw) void lostInfo(){
        startActivity(new Intent(LoginActivity.this, LostInfoActivity.class));
    }
    // case Click SNS Login
    @OnClick(R.id.login_btn_snslogin) void snsLogin(){

    }

    // 아이디 패스워드 입력란 코드
    public boolean validateLogin(String id, String pwd) {
        if (id == null || id.trim().length() == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pwd == null || pwd.trim().length() == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
