package com.seok.seok.wowsup.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseLogin;
import com.seok.seok.wowsup.retrofit.model.ResponseMail;
import com.seok.seok.wowsup.retrofit.remote.ApiMailUtils;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements Dialog.OnCancelListener {
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    private LayoutInflater dialog; //LayoutInflater
    private View dialogLayout; //layout View
    private Dialog authDialog; //dialog Object
    private TextView timeCounter; //시간을 보여주는 TextView
    private EditText authNumber; //인증 번호를 입력 하는 칸
    private Button btnOver; // 인증버튼
    private CountDownTimer countDownTimer;
    private Callback retrofitCallBack;

    //FireBase 관련
    private FirebaseAuth auth;

    @BindView(R.id.reg_edt_id)    EditText edtID;
    @BindView(R.id.reg_edt_pw)    EditText edtPW;
    @BindView(R.id.reg_edt_email)    EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        init();
    }

    @OnClick(R.id.reg_btn_signup)    void signUp() {
        if(checkInfo(Common.confirmID, Common.confirmEmail, edtEmail.getText().toString())){
            ApiUtils.getUserService().doRegister(edtID.getText().toString(), edtPW.getText().toString(), edtEmail.getText().toString()).enqueue(retrofitCallBack);
        }
    }
    @OnClick(R.id.reg_btn_conid)    void confirmID() {
        if (edtID.getText().toString().isEmpty() || edtID.getText().toString().length() < 4) {
            Toast.makeText(RegisterActivity.this, "The ID field requires at least 4 characters.", Toast.LENGTH_SHORT).show();
        } else {
            ApiUtils.getUserService().confirmID(edtID.getText().toString()).enqueue(retrofitCallBack);
        }
    }

    @OnClick(R.id.reg_btn_conemail)    void confirmEmail() {
        if (!TextUtils.isEmpty(edtEmail.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            showAuthDialog();
            ApiMailUtils.getEmailService().authEmail(edtEmail.getText().toString(), Common.mailRandNum()).enqueue(new Callback<ResponseMail>() {
                @Override
                public void onResponse(Call<ResponseMail> call, Response<ResponseMail> response) {
                    Log.d("WowSup_Register_HTTP", "http trans Success");
                    if(response.isSuccessful()){
                        Log.d("WowSup_Register_HTTP", "http response Success");
                        ResponseMail body = response.body();
                        if (body.getState() == 0) {
                            Toast.makeText(RegisterActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        } else if (body.getState() == 1) {
                            Toast.makeText(RegisterActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseMail> call, Throwable t) {
                    Log.d("WowSup_Register_HTTP", "http trans Failed");
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "Please enter a valid email format.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.reg_btn_login)    void login() {
        finish();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        countDownTimer.cancel();
    }

    public void countDownTimer() { //카운트 다운 메소드
        timeCounter = dialogLayout.findViewById(R.id.auth_txt_time);        //줄어드는 시간을 나타내는 TextView
        authNumber = dialogLayout.findViewById(R.id.auth_edt_num);        //사용자 인증 번호 입력창
        btnOver = dialogLayout.findViewById(R.id.auth_btn_over);        //인증하기 버튼
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)
                long emailAuthCount = millisUntilFinished / 1000;
                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    timeCounter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    timeCounter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.
            }

            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료
                authDialog.cancel();
            }
        }.start();
        btnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(Integer.parseInt(authNumber.getText().toString()) == Common.randNum){
                        Toast.makeText(RegisterActivity.this, "Email authentication successful", Toast.LENGTH_SHORT).show();
                        Common.confirmEmail = true;
                        countDownTimer.cancel();
                        authDialog.cancel();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Email authentication failed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this, "Invalid verification number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void fireBaseReg(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("WowSup_Register_FB", "FB trans Success");
                }else {
                    Log.d("WowSup_Register_FB", "FB trans Failed");
                }
            }
        });
    }
    public void init() {
        retrofitCallBack = new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                Log.d("WowSup_Register_HTTP", "http trans Success");
                if (response.isSuccessful()) {
                    ResponseLogin body = response.body();
                    if (body.getState() == 0) {
                        Toast.makeText(RegisterActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        if(checkInfo(Common.confirmID, Common.confirmEmail, edtPW.getText().toString())){
                            fireBaseReg(edtEmail.getText().toString(), edtPW.getText().toString());
                            finish();
                        }
                        Common.confirmID = true;
                    } else {
                        Toast.makeText(RegisterActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        Common.confirmID = false;
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.d("WowSup_Register_HTTP", "http trans Failed");
            }
        };
    }
    protected void showAuthDialog(){
        dialog = LayoutInflater.from(this);
        dialogLayout = dialog.inflate(R.layout.dialog_email_auth, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
        authDialog = new Dialog(this);
        authDialog.setContentView(dialogLayout); //Dialog에 inflate한 View를 탑재 하여줌
        authDialog.setCanceledOnTouchOutside(false); //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.
        authDialog.setOnCancelListener(this); //다이얼로그를 닫을 때 일어날 일을 정의하기 위해 onCancelListener 설정
        authDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(RegisterActivity.this, "Please check your e-mail", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        authDialog.show(); //Dialog를 나타내어 준다.
        countDownTimer();
    }
    protected boolean checkInfo(boolean confirmID, boolean confirmEmail, String pwd){
        if(!confirmID){
            Toast.makeText(RegisterActivity.this, "Please check your ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!confirmEmail){
            Toast.makeText(RegisterActivity.this, "Please check your E-Mail", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pwd.isEmpty() || pwd.length() < 6){
            Toast.makeText(RegisterActivity.this, "The PW field requires at least six characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
