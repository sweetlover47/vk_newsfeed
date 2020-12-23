package com.example.vk_try2;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ItemDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Item>> itemLiveDataSource = new MutableLiveData<>();
    public static PostAdapter mAdapter;

    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Item>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
