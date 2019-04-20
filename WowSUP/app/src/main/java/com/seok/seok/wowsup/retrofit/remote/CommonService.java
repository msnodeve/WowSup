package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseCommon;
import com.seok.seok.wowsup.retrofit.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommonService {

    // Server request URL

    @GET("Common/applyFriend.php")
    Call<ResponseCommon> applyFriend(@Query("applyer") String applyer,
                                     @Query("applyed") String applyed);
}
