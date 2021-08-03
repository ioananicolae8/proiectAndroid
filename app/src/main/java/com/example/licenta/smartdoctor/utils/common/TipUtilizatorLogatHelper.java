package com.example.licenta.smartdoctor.utils.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.licenta.smartdoctor.activities.MainActivity;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;

public class TipUtilizatorLogatHelper {

    public static void seteazaTipUtilizatorLogat(Context context, ETipUtilizator tipUtilizator) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tipUtilizatorLogat", tipUtilizator != null ? tipUtilizator.name() : null);
        editor.apply();
        MainActivity.tipUtilizatorLogat = tipUtilizator;
    }
}
