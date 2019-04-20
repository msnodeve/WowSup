package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseProfile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProfileService {
    //서버 요청 URL

    @POST("Profile/profile.php")
    Call<ResponseProfile> profile(@Query("userID") String userID);

    @POST("Profile/updateProfile.php")
    Call<ResponseProfile> updateProfile(@Query("userID") String userID,
                                        @Query("userAge") int userAge,
                                        @Query("userGender") String userGender,
                                        @Query("userCountry") String userCountry,
                                        @Query("userSelfish") String userSelf,
                                        @Query("userBanner") int userBanner,
                                        @Query("userChange") int change);
}
