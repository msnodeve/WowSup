package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseChat;
import com.seok.seok.wowsup.retrofit.model.ResponseStore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//채팅 서비스 인터페이스
public interface ChatService {
    //서버 요청 URL
    @POST("Chat/listFriend.php")
    Call<List<ResponseChat>> getFriendList(@Query("userID") String userID);

    @POST("Chat/updateFriendNick.php")
    Call<ResponseChat> setUpdateNick(@Query("userID") String userID,
                                     @Query("friendID") String friendID,
                                     @Query("nick") String nick);

    @POST("Chat/deleteFriend.php")
    Call<ResponseChat> deleteFriend(@Query("userID") String userID,
                                     @Query("friendID") String friendID);

}
