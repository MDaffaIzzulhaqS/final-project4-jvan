package com.finalprojectkelompok6.finalproject4_backup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.finalprojectkelompok6.finalproject4_backup.MainActivity;
import com.finalprojectkelompok6.finalproject4_backup.R;
import com.finalprojectkelompok6.finalproject4_backup.Utility;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    EditText emailEt, passEt;
    LinearLayout signInBtn;
    ProgressBar progressBar;
    TextView tvCreateAccount;
    LinearLayout googlebtn;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEt = findViewById(R.id.et_email_sign_in);
        passEt = findViewById(R.id.et_password_sign_in);
        signInBtn = findViewById(R.id.linear_button_signin);
        progressBar = findViewById(R.id.loading);
        tvCreateAccount = findViewById(R.id.tv_create_account);

        signInBtn.setOnClickListener((v) -> loginUser());

        tvCreateAccount.setOnClickListener((v) -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));

        // Google
        googlebtn = findViewById(R.id.linear_button_login_google);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        googlebtn.setOnClickListener((v) -> loginGoogle());

    }

    // Google
    private void loginGoogle() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Utility.showToast(SignInActivity.this, "Gagal");
        }


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Log.d(TAG, "Success Signin Google:" + account.getId() +" - "+account.getDisplayName());
            Log.d(TAG, "Id:" + account.getId());
            Log.d(TAG, "Id Token:" + account.getIdToken());
            Log.d(TAG, "Display Name:" +account.getDisplayName());

            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(), "Something Went Wrong! "+"signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        String email = emailEt.getText().toString();
        String password = passEt.getText().toString();

        boolean isValidated = validateData(email, password);
        if (!isValidated) {
            return;
        }

        signInAccountInfirebase(email, password);
    }

    void signInAccountInfirebase(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            changeInProgress(false);
            if (task.isSuccessful()) {
                // Sign In Succes
                if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()) {
                    // Go To MainActivity
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                } else {
                    Utility.showToast(SignInActivity.this, "Harap Verifikasi Email Terlebih Dahulu!");
                }

            } else {
                // Sign In Failed
                Utility.showToast(SignInActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage());

            }
        });


    }


    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            signInBtn.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            signInBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEt.setError("Email Salah");
            return false;
        }
        if (password.length() <= 6) {
            passEt.setError("Password harus lebih dari 6 karakter");
            return false;
        }

        return true;

    }


}
