package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseStore;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//스토어 서비스 인터페이스
public interface StoreService {
    //서버 요청 URL
    @GET("Common/buyToken.php")
    Call<ResponseStore> updateToken(@Query("userID") String userID,
                                    @Query("token") int token);
}
