package com.seok.seok.wowsup.utilities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.seok.seok.wowsup.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Common {
    //서버 기본 주소
    public static final String STORY_IMAGE_BACK_BASE_URL = "http://www.heywowsup.com/wowsup/Image/Background/";

    // Email 관련
    public static int randNum;
    public static boolean confirmEmail = false;
    public static boolean confirmID = false;

    // Story 관련
    public static String imgStoryURL = "basic_image_1_st.png";

    // Camera 관련
    public static final int FROM_ALBUM = 1;
    public static final int FROM_CAMERA = 2;

    //Country 관련
    public static ArrayList<Country> countryArrayList;

    //Wowsup Color
    public static final int[] WOWSUP_COLOR = {
            Color.rgb(201, 223, 241),
            Color.rgb(239, 231, 204),
            Color.rgb(240, 244, 244),
            Color.rgb(126, 128, 130),
            Color.rgb(239, 191, 168),
            Color.rgb(218, 211, 206),
            Color.rgb(100, 117, 122),
            Color.rgb(146, 182, 187),
            Color.rgb(183, 187, 189),
            Color.rgb(246, 224, 209)
    };

    //Banner Select Resource
    public static final int[] PICK_BANNER = {
            R.drawable.click_color_1_st,
            R.drawable.click_color_2_nd,
            R.drawable.click_color_3_rd,
            R.drawable.click_color_4_th,
            R.drawable.click_color_5_th,
            R.drawable.click_color_6_th,
            R.drawable.click_color_7_th,
            R.drawable.click_color_8_th,
            R.drawable.click_color_9_th,
            R.drawable.click_color_10_th,
    };
    public static int mailRandNum() {
        Random rand = new Random();
        randNum = rand.nextInt(999999 - 100000 + 1) + 100000;
        return randNum;
    }

    //파일 사이즈 2MB이상 업로드 중지
    public static boolean fileUpload(File file) {
        if (!file.exists())
            return false;
        if (file.length() > 2097152)
            return false;
        return true;
    }

    public static boolean uploadConfirm(Context context, String title, String body){
        if(title.isEmpty() || title.length() == 0){
            Toast.makeText(context, "The title field is empty.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (body.isEmpty() || body.length() == 0) {
            Toast.makeText(context, "The contents field is empty.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(title.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
                Toast.makeText(context, "The title contains non-English words.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (body.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                Toast.makeText(context, "The contents contains non-English words.", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }
        }
    }
    public static void setCountry(){
        countryArrayList = new ArrayList<>();
        countryArrayList.add(new Country("Korea", R.drawable.country_south_korea));
        countryArrayList.add(new Country("UnitedKingdom(UK)", R.drawable.country_uk));
        countryArrayList.add(new Country("United States of America(USA)", R.drawable.country_usa));
        countryArrayList.add(new Country("Japan", R.drawable.country_japan));
        countryArrayList.add(new Country("China", R.drawable.country_china));
        countryArrayList.add(new Country("Taiwan", R.drawable.country_taiwan));
        countryArrayList.add(new Country("Canada", R.drawable.country_canada));
    }



}
