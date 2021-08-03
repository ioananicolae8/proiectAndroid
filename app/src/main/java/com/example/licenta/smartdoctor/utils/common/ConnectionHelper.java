package com.example.licenta.smartdoctor.utils.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

public class ConnectionHelper {

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        if (!isConnected) {
            closeApp(context);
        }
        return isConnected;
    }

    private static void closeApp(final Context context) {
        ((AppCompatActivity) context).setFinishOnTouchOutside(false);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog
                .setTitle("Nu există conexiune la Internet!")
                .setMessage("Conectează-te și încearcă din nou!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((AppCompatActivity) context).finishAffinity();
                        System.exit(0);
                    }
                });
        alertDialog.show();
    }
}
