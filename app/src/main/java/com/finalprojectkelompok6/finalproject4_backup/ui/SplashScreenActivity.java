package com.finalprojectkelompok6.finalproject4_backup.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.finalprojectkelompok6.finalproject4_backup.MainActivity;
import com.finalprojectkelompok6.finalproject4_backup.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) { // Auth
                startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
            } else {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }

            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
            if (googleSignInAccount != null) { // Google SignIn
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }

            finish();
        }, 3000);

    }
}