package com.example.licenta.smartdoctor.utils.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastHelper {
    private static final int OFFSET = 0;
    private static final int INDEX = 0;

    public static void showToastWithImage(Context context, String text, int imageResource) {
        Toast imageToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        imageToast.setGravity(Gravity.CENTER, OFFSET, OFFSET);
        LinearLayout toastContentView = (LinearLayout) imageToast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageResource);
        toastContentView.addView(imageView, INDEX);
        imageToast.show();
    }

    public static void showToastWithImage(Context context, String text, int imageResource, int length) {
        Toast imageToast = Toast.makeText(context, text, length);
        imageToast.setGravity(Gravity.CENTER, OFFSET, OFFSET);
        LinearLayout toastContentView = (LinearLayout) imageToast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageResource);
        toastContentView.addView(imageView, INDEX);
        imageToast.show();
    }
}
