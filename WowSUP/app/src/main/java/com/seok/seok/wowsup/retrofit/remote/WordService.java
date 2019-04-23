package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseLogin;
import com.seok.seok.wowsup.retrofit.model.ResponseWord;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WordService {

    // Server request URL
    @POST("Chat/chatWord.php")
    Call<ResponseWord> requestChatWord(@Body Map<String, String> wordMap);
}
