package com.finalprojectkelompok6.finalproject4_backup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalprojectkelompok6.finalproject4_backup.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imgBackNotif.setOnClickListener(view1 -> startActivity(new Intent(NotificationActivity.this, MainActivity.class)));
    }
}
