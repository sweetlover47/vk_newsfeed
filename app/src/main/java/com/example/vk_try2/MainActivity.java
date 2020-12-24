package com.example.vk_try2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            VkClient.TOKEN = savedInstanceState.getString("token");
        }
        final Button button = findViewById(R.id.button);
        button.setOnClickListener((e) -> login());
    }

    private void login() {
        if (!(VkClient.TOKEN != null && VkClient.TOKEN.equals(""))) {
            startNewsFeedActivity();
        } else {
            VK.login(this, new ArrayList<>(Arrays.asList(VKScope.WALL, VKScope.PHOTOS, VKScope.FRIENDS, VKScope.OFFLINE)));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        VKAuthCallback callback = new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                Toast.makeText(getApplicationContext(), "Авторизация прошла успешно", Toast.LENGTH_LONG).show();
                VkClient.TOKEN = vkAccessToken.getAccessToken();
                startNewsFeedActivity();
            }

            @Override
            public void onLoginFailed(int i) {
                Toast.makeText(getApplicationContext(), "Авторизация прошла фатально", Toast.LENGTH_LONG).show();
            }
        };
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startNewsFeedActivity() {
        Intent intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("token", VkClient.TOKEN);
    }

}