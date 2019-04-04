package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseWordChart;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WordChartService {
    //서버 요청 URL

    @POST("Global/chatWord.php")
    Call<List<ResponseWordChart>> requestChatWord(@Body Map<String, String> wordMap);

    @GET("Global/globalWordChart.php")
    Call<List<ResponseWordChart>> wordChart();

}