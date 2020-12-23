package com.example.vk_try2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("newsfeed.get")
    Call<VkApiResponse> getNewsFeeds(
            @Query("start_from") String startFrom,
            @Query("count") int count,
            @Query("access_token") String token,
            @Query("v") String version
    );
}
