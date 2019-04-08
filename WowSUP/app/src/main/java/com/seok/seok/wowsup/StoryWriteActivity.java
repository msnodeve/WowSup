package com.seok.seok.wowsup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.seok.seok.wowsup.utilities.Common.FROM_ALBUM;
import static com.seok.seok.wowsup.utilities.Common.FROM_CAMERA;
import static com.seok.seok.wowsup.utilities.Common.imgStoryURL;

public class StoryWriteActivity extends AppCompatActivity {
    private Uri imgUri;
    private String mCurrentPhotoPath;

    @BindView(R.id.write_edt_tag1)
    EditText edtTag1;
    @BindView(R.id.write_edt_tag2)
    EditText edtTag2;
    @BindView(R.id.write_edt_tag3)
    EditText edtTag3;
    @BindView(R.id.write_edt_tag4)
    EditText edtTag4;
    @BindView(R.id.write_edt_tag5)
    EditText edtTag5;
    @BindView(R.id.write_edt_title)
    EditText edtTitle;
    @BindView(R.id.write_edt_body)
    EditText edtBody;
    @BindView(R.id.write_layout_back)
    LinearLayout layoutBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_write);
        ButterKnife.bind(this);
        cameraPermission();
    }

    @OnClick(R.id.write_ibtn_back)
    void back() {
        finish();
    }

    @OnClick(R.id.write_ibtn_help)
    void goHelp() {

    }

    @OnClick(R.id.write_ibtn_back1)
    void setBackground1() {
        layoutBack.setBackgroundResource(R.drawable.basic_image_1_st);
        imgStoryURL = "basic_image_1_st.png";
    }

    @OnClick(R.id.write_ibtn_back2)
    void setBackground2() {
        layoutBack.setBackgroundResource(R.drawable.basic_image_2_nd);
        imgStoryURL = "basic_image_2_nd.png";
    }

    @OnClick(R.id.write_ibtn_back3)
    void setBackground3() {
        layoutBack.setBackgroundResource(R.drawable.basic_image_3_rd);
        imgStoryURL = "basic_image_3_rd.png";
    }

    @OnClick(R.id.write_ibtn_back4)
    void setBackground4() {
        layoutBack.setBackgroundResource(R.drawable.basic_image_4_th);
        imgStoryURL = "basic_image_4_th.png";
    }

    @OnClick(R.id.write_ibtn_back5)
    void setBackground5() {
        layoutBack.setBackgroundResource(R.drawable.basic_image_5_th);
        imgStoryURL = "basic_image_5_th.png";
    }

    @OnClick(R.id.write_ibtn_picture)
    void setUserPicture() {
        makeDialog();
    }

    @OnClick(R.id.write_btn_save)
    void save() {

    }

    //카메라 권한 확인
    private void cameraPermission() {
        //마쉬멜로우버전 이후, 권한을 요청 요망
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //권한 승인
                        Toast.makeText(StoryWriteActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        //권한 거부
                        Toast.makeText(StoryWriteActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                TedPermission tedPermission = new TedPermission();
                tedPermission.with(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission] ")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();
            }
        }
    }
    // 사용자 이미지 업로드
    private void makeDialog() {
        AlertDialog.Builder altBld = new AlertDialog.Builder(StoryWriteActivity.this);
        altBld.setTitle("Upload photos").setIcon(R.drawable.camera_icon).setCancelable(
                false).setPositiveButton("Photo shoot",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("WowSup_StoryWrite", "dialog > take picture");
                        //takePhoto();
                    }
                }).setNeutralButton("Select album",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Log.d("WowSup_StoryWrite", "dialog > get picture");
                        selectAlbum();
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("WowSup_StoryWrite", "dialog > cancel");
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = altBld.create();
        alertDialog.show();
    }

    //앨범 선택 클릭
    public void selectAlbum(){
        //앨범 열기
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUM);
    }

    //사진 찍기 클릭
    public void takePhoto(){
        // 촬영 후 이미지 가져옴
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(photoFile!=null){
                    Uri providerURI = FileProvider.getUriForFile(this,getPackageName(), photoFile);
                    imgUri = providerURI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }
        }else{
            Log.v("알림", "저장공간에 접근 불가능");
            return;
        }
    }

    public File createImageFile() throws IOException{
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile= null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }
        Log.v("알림","storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir,imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        Log.d("asdfasdf",mCurrentPhotoPath);
        return imageFile;
    }
}
