package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    // Server request URL

    @POST("Login/doLogin.php")
    Call<ResponseLogin> doLogin(@Query("userID") String userID,
                                @Query("userPW") String userPW);

    @POST("Login/confirmID.php")
    Call<ResponseLogin> confirmID(@Query("userID") String userID);

    @POST("Login/doRegister.php")
    Call<ResponseLogin> doRegister(@Query("userID") String userID,
                                   @Query("userPW") String userPW,
                                   @Query("userEmail") String userEmail);
}
