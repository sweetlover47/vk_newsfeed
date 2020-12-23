package com.example.vk_try2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

public class PostViewModel extends ViewModel {
    LiveData<PagedList<Item>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Item>> liveDataSource;

    public PostViewModel() {
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGE_SIZE)
                .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();

    }
}
