package com.finalprojectkelompok6.finalproject4_backup.ui;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.finalprojectkelompok6.finalproject4_backup.R;

public class EditProfileActivity extends AppCompatActivity {

    EditText nameEt, emailEt, passEt, phoneNumberEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEt = findViewById(R.id.et_name);
        emailEt = findViewById(R.id.et_emailuser);
        passEt = findViewById(R.id.et_password);
        phoneNumberEt = findViewById(R.id.et_phone_number);
    }

}
