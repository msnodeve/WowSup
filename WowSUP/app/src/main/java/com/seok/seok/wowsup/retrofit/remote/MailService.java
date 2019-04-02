package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseMail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MailService {
    //서버 요청 URL

    @GET("authEmail.php")
    Call<ResponseMail> authEmail(@Query("userEmail") String email,
                                 @Query("rand") int rand);

    @POST("forgetID.php")
    Call<ResponseMail> findID(@Query("userEmail") String email);

    @POST("forgetPW.php")
    Call<ResponseMail> findPW(@Query("userID") String id,
                              @Query("userEmail") String email);
}