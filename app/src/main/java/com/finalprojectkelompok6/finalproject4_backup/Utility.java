package com.finalprojectkelompok6.finalproject4_backup;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}