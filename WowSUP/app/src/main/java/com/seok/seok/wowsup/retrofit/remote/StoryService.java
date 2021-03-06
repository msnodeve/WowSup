package com.seok.seok.wowsup.retrofit.remote;

import com.seok.seok.wowsup.retrofit.model.ResponseStory;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

//스토리 서비스 인터페이스
public interface StoryService {
    //서버 요청 URL

    @POST("Story/myStory.php")
    Call<List<ResponseStory>> myStory(@Query("userID") String userID);

    @POST("Story/pickStory.php")
    Call<ResponseStory> pickStory(@Query("userID") String userID,
                                  @Query("storyID") String storyID);

    @POST("Story/likeStory.php")
    Call<ResponseStory> likeStory(@Query("userID") String userID,
                                  @Query("storyID") String storyID);

    @POST("Story/deleteStory.php")
    Call<ResponseStory> deleteStory(@Query("userID") String userID,
                                    @Query("storyID") String storyID);

    @POST("Story/banStory.php")
    Call<ResponseStory> banStory(@Query("userID") String userID,
                                 @Query("storyID") String storyID);

    @GET("Story/showStory.php")
    Call<List<ResponseStory>> showStory(@Query("start") int start);

    @GET("Story/searchTag.php")
    Call<List<ResponseStory>> searchTag(@Query("tag") String tag);

    @POST("Story/uploadStory.php")
    Call<ResponseStory> uploadStory(@Query("userID") String id,
                                    @Query("title") String title,
                                    @Query("body") String body,
                                    @Query("image") String image,
                                    @Query("tag1") String tag1,
                                    @Query("tag2") String tag2,
                                    @Query("tag3") String tag3,
                                    @Query("tag4") String tag4,
                                    @Query("tag5") String tag5);

    @Multipart
    @POST("Story/uploadImageStory.php")
    Call<ResponseStory> uploadImageStory(@Query("userID") String id,
                                         @Query("title") String title,
                                         @Query("body") String body,
                                         @Query("tag1") String tag1,
                                         @Query("tag2") String tag2,
                                         @Query("tag3") String tag3,
                                         @Query("tag4") String tag4,
                                         @Query("tag5") String tag5,
                                         @Part MultipartBody.Part file,
                                         @Part("file") RequestBody name);

    @GET("Story/recommendTag.php")
    Call<ResponseStory> recommendTag(@Query("number") int randNum);
}