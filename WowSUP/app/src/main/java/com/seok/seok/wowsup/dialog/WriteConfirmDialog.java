package com.seok.seok.wowsup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseStory;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteConfirmDialog extends Dialog {
    @BindView(R.id.dialog_layout)
    LinearLayout layout;
    @BindView(R.id.dialog_txt_qna)
    TextView txtQnA;
    private Context context;
    private Callback retrofitCallBack;
    private String strQnA, title, body, image, tag1, tag2, tag3, tag4, tag5;
    private boolean imgFlag;
    public WriteConfirmDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        ButterKnife.bind(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(layout.getLayoutParams());
        layoutParams.width = (int) (GlobalWowSup.getInstance().getUserWidth() / 1.3);
        layoutParams.height = (int) (GlobalWowSup.getInstance().getUserHeight() / 3.5);
        layout.setLayoutParams(layoutParams);
        txtQnA.setText(strQnA);
        retrofitCallBack = new Callback<ResponseStory>() {
            @Override
            public void onResponse(Call<ResponseStory> call, Response<ResponseStory> response) {
                Log.d("WowSup_WriteDialog_HTTP", "http trans Success");
                if (response.isSuccessful()) {
                    ResponseStory body = response.body();
                    if (body.getState() == 0) {
                        Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "Upload Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<ResponseStory> call, Throwable t) {
                Log.d("WowSup_WriteDialog_HTTP", "http trans Failed");
                Toast.makeText(context, "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @OnClick(R.id.dialog_btn_yes)
    void confirmYes() {
        uploadFile();
    }

    @OnClick(R.id.dialog_btn_no)
    void confirmNo() {
        dismiss();
    }

    protected void uploadFile() {
        try {
            if(imgFlag) {
                File file = new File(image);
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", GlobalWowSup.getInstance().getId() + "_" + file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                ApiUtils.getStoryService().uploadImageStory(GlobalWowSup.getInstance().getId(), title, body, tag1, tag2, tag3, tag4, tag5, fileToUpload, filename).enqueue(retrofitCallBack);
            }else{
                ApiUtils.getStoryService().uploadStory(GlobalWowSup.getInstance().getId(), title, body, Common.STORY_IMAGE_BACK_BASE_URL + image, tag1, tag2, tag3, tag4, tag5).enqueue(retrofitCallBack);
            }
        } catch (Exception e) {
            Log.d("WowSup_WriteDialog_Upload", "Upload Failed");
        }
    }

    public void setData(String title, String body, String image, String tag1, String tag2, String tag3, String tag4, String tag5) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.tag5 = tag5;
    }
    public void setImgFlag(boolean imgFlag){
        this.imgFlag = imgFlag;
    }
    public void setTxtQnA(String strQnA) {
        this.strQnA = strQnA;
    }
}
