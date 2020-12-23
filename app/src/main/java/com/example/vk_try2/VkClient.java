package com.example.vk_try2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VkClient {
    public static final String BASE_URL = "https://api.vk.com/method/";
    public static final String VERSION = "5.126";
    public static String TOKEN = "";

    private static VkClient mInstance;
    private Retrofit retrofit;

    public VkClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized VkClient
    getInstance() {
        if (mInstance == null) {
            mInstance = new VkClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
