package com.example.licenta.smartdoctor.utils.common;

import android.content.Context;

import com.example.licenta.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationHelper {
    private static final int MIN_AGE = 8;
    private static final int MAX_AGE = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String DEFAULT_AVATAR_URL = "https://firebasestorage.googleapis.com/v0/b/licenta-5c360.appspot.com" +
        "/o/Poza_de_profil%2Ficon_avatar.png?alt=media&token=252b7bb4-a168-4c95-83a2-c4b55b1abd7a";
    private static final int PHONE_LENGTH = 10;
    private static final int CNP_LENGTH = 13;



    public static boolean isPasswordValid(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    public static boolean isUsernameValid(String username) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isAgeValid(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    public static String getUserAvatarPath(Context context, String userId) {
        return context.getString(R.string.avatars_directory) + userId;
    }

    public static String getDefaultAvatarUrl() {
        return DEFAULT_AVATAR_URL;
    }

    public static boolean isPhoneValid(String phoneRaw) {
        return phoneRaw.length() == PHONE_LENGTH;
    }

    public static boolean isCNPValid(String cnpRaw) {
        return cnpRaw.length() == CNP_LENGTH;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isDataNastereValid(String data_nastereRaw) {
        Pattern pattern1 = Pattern.compile("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]" +
                "|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])" +
                "(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)");
        Matcher matcher1 = pattern1.matcher(data_nastereRaw);
        return matcher1.matches();
    }
    
}

