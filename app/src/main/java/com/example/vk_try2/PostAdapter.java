package com.example.vk_try2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends PagedListAdapter<Item, PostAdapter.PostViewHolder> {
    Context mContext;
    List<Profile> mProfiles;
    List<Group> mGroups;

    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.date == newItem.date && oldItem.source_id == newItem.source_id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected PostAdapter(Context context) {
        super(DIFF_CALLBACK);
        mContext = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Item item = getItem(position);
        Profile profile = null;
        Group group = null;
        if (item != null) {
            if (item.source_id < 0 && mGroups != null) {
                group = mGroups.stream()
                        .filter(g -> -item.source_id == g.id)
                        .findAny()
                        .orElse(null);
            } else if (item.source_id > 0 && mProfiles != null) {
                profile = mProfiles.stream()
                        .filter(p -> item.source_id == p.id)
                        .findAny()
                        .orElse(null);
            }

            if (item.source_id < 0 && group != null) {
                Glide.with(mContext)
                        .load(group.photo_100)
                        .into(holder.imageView);
                holder.imageView.setContentDescription(group.photo_100);
                holder.authorTextView.setText(group.name);
            } else if (profile != null) {
                Glide.with(mContext)
                        .load(profile.photo_100)
                        .into(holder.imageView);
                holder.imageView.setContentDescription(profile.photo_100);
                holder.authorTextView.setText(String.format("%s %s", profile.first_name, profile.last_name));
            }
            if (item.text != null && !item.text.trim().isEmpty()) {
                holder.mText = item.text;
                holder.contentTextView.setText(item.text.length() > 200 ? item.text.substring(0, 200) + "..." : item.text);
            } else {
                holder.mText = "Здесь должны быть картинки ฅ•ω•ฅ";
                holder.contentTextView.setText(holder.mText);
            }
        } else {
            Toast.makeText(mContext, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView authorTextView;
        TextView contentTextView;
        RelativeLayout postView;
        String mText = "";


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            authorTextView = itemView.findViewById(R.id.textViewAuthor);
            imageView = itemView.findViewById(R.id.imageView);
            contentTextView = itemView.findViewById(R.id.contentView);
            postView = itemView.findViewById(R.id.clickableView);
            postView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, ItemActivity.class);
            intent.putExtra("author", authorTextView.getText());
            intent.putExtra("content", mText);
            intent.putExtra("image", imageView.getContentDescription());
            mContext.startActivity(intent);
        }
    }
}
