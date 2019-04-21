package com.seok.seok.wowsup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowSup;
import com.seok.seok.wowsup.wowsup.StoryWriteActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.seok.seok.wowsup.wowsup.StoryWriteActivity.edtBody;
import static com.seok.seok.wowsup.wowsup.StoryWriteActivity.edtTitle;

public class TranslateDialog extends Dialog {
    @BindView(R.id.trans_layout)
    LinearLayout layout;
    @BindView(R.id.trans_edt_origin)
    EditText edtOrigin;
    @BindView(R.id.trans_txt_origin)
    TextView txtOrigin;
    private String clientID, clientSecret;

    public TranslateDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_translate);
        ButterKnife.bind(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(layout.getLayoutParams());
        layoutParams.width = (int) (GlobalWowSup.getInstance().getUserWidth() / 1.2);
        layoutParams.height = (int) (GlobalWowSup.getInstance().getUserHeight() / 1.5);
        layoutParams.gravity = Gravity.CENTER;
        layout.setLayoutParams(layoutParams);
        clientID = "Ua0mUlA_qaRPeQRfH8MW";
        clientSecret = "JbA1vetrhA";
    }
    @OnClick(R.id.trans_btn_post) void post(){
        if(Common.translateOption==1)
            edtTitle.setText(edtTitle.getText().toString() + txtOrigin.getText().toString());
        else if(Common.translateOption == 2)
            edtBody.setText(edtBody.getText().toString() + txtOrigin.getText().toString());
        else if(Common.translateOption == 3)
            edtTitle.setText(edtTitle.getText().toString() + txtOrigin.getText().toString());
        dismiss();
    }
    @OnClick(R.id.trans_ibtn_back) void goBack(){
        dismiss();
    }
    @OnClick(R.id.trans_btn_trans)
    void translate() {
        new Thread() {
            public void run() {
                try {
                    String text = URLEncoder.encode(edtOrigin.getText().toString(), "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/language/translate";
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("X-Naver-Client-Id", clientID);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    // post request
                    String postParams = "source=ko&target=en&text=" + text;
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(postParams);
                    wr.flush();
                    wr.close();
                    int responseCode = con.getResponseCode();
                    BufferedReader br;
                    if (responseCode == 200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();
                    txtOrigin.setText(parsing(response.toString()).replace("\\n", ""));
                } catch (Exception e) {}
            }
        }.start();
    }

    public String parsing(String txtTrans) {
        String strReturn = txtTrans;
        String array1[] = strReturn.split(":");
        String array2[] = array1[6].split("\"");
        return array2[1];
    }
}