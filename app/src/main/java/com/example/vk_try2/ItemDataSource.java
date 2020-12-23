package com.example.vk_try2;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {
    public static final int PAGE_SIZE = 50;
    public static int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Item> callback) {
        VkClient.getInstance()
                .getApi()
                .getNewsFeeds(String.valueOf(FIRST_PAGE), PAGE_SIZE, VkClient.TOKEN, VkClient.VERSION)
                .enqueue(new Callback<VkApiResponse>() {
                    @Override
                    public void onResponse(Call<VkApiResponse> call, Response<VkApiResponse> response) {
                        if (response.body() != null) {
                            ItemDataSourceFactory.mAdapter.mGroups = response.body().response.groups;
                            ItemDataSourceFactory.mAdapter.mProfiles = response.body().response.profiles;
                            callback.onResult(response.body().response.items, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<VkApiResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Item> callback) {
        VkClient.getInstance()
                .getApi()
                .getNewsFeeds(String.valueOf(params.key), PAGE_SIZE, VkClient.TOKEN, VkClient.VERSION)
                .enqueue(new Callback<VkApiResponse>() {
                    @Override
                    public void onResponse(Call<VkApiResponse> call, Response<VkApiResponse> response) {
                        if (response.body() != null) {
                            ItemDataSourceFactory.mAdapter.mGroups = response.body().response.groups;
                            ItemDataSourceFactory.mAdapter.mProfiles = response.body().response.profiles;
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body().response.items, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<VkApiResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Item> callback) {
        VkClient.getInstance()
                .getApi()
                .getNewsFeeds(String.valueOf(params.key), PAGE_SIZE, VkClient.TOKEN, VkClient.VERSION)
                .enqueue(new Callback<VkApiResponse>() {
                    @Override
                    public void onResponse(Call<VkApiResponse> call, Response<VkApiResponse> response) {
                        if (response.body() != null) {
                            ItemDataSourceFactory.mAdapter.mGroups = response.body().response.groups;
                            ItemDataSourceFactory.mAdapter.mProfiles = response.body().response.profiles;
                            Integer key = params.key + 1;
                            callback.onResult(response.body().response.items, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<VkApiResponse> call, Throwable t) {

                    }
                });
    }
}
