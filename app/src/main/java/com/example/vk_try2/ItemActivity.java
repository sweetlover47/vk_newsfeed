package com.example.vk_try2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        final TextView author = findViewById(R.id.textViewAuthor);
        author.setText(intent.getStringExtra("author"));
        final TextView content = findViewById(R.id.contentView);
        content.setText(intent.getStringExtra("content"));
        final ImageView imageView = findViewById(R.id.imageView);
        Glide.with(getApplicationContext())
                .load(intent.getStringExtra("image"))
                .into(imageView);
    }
}