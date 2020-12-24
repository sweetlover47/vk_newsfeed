package com.example.vk_try2;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class NewsFeedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Parcelable mManagerState;
    PostAdapter mAdapter;
    PostViewModel mPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        mAdapter = new PostAdapter(this);
        if (savedInstanceState != null) {
            mManagerState = savedInstanceState.getParcelable("layout");
            requireNonNull(recyclerView.getLayoutManager()).onRestoreInstanceState(mManagerState);
            ItemDataSource.FIRST_PAGE = savedInstanceState.getInt("first");
            mAdapter.mGroups = (List<Group>) savedInstanceState.getSerializable("groups");
            mAdapter.mProfiles = (List<Profile>) savedInstanceState.getSerializable("profiles");
        }
        ItemDataSourceFactory.mAdapter = mAdapter;

        requireNonNull(mPostViewModel).itemPagedList.observe(this, mAdapter::submitList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mManagerState = requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("first", ItemDataSource.FIRST_PAGE);
        savedInstanceState.putParcelable("layout", mManagerState);
        savedInstanceState.putSerializable("groups", new ArrayList<>(mAdapter.mGroups));
        savedInstanceState.putSerializable("profiles", new ArrayList<>(mAdapter.mProfiles));
    }
}