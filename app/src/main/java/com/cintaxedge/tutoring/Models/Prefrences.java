package com.cintaxedge.tutoring.Models;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Prefrences {

    public static void saveUserDetails(Context context, LoginResponse loginResponse) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", loginResponse.getUserDetails().getUserId());
        editor.putString("userName", loginResponse.getUserDetails().getUserName());
        editor.putString("email", loginResponse.getUserDetails().getUserName());
        editor.putString("mobile", loginResponse.getUserDetails().getMobile());
        editor.putString("name", loginResponse.getUserDetails().getName());
        editor.putString("authorizationToken", loginResponse.getUserDetails().getAuthorizationToken());
        editor.putBoolean("isLoggedIn", true);
        editor.putBoolean("isUserSubscribed", loginResponse.getUserDetails().isUserSubscribed());
        editor.apply();
    }

    public static void logout(Context context) {
        SharedPreferences.Editor prefs = context.getSharedPreferences("userDetail", Context.MODE_PRIVATE).edit();

        prefs.putString("userId", "");
        prefs.putString("userName", "");
        prefs.putString("email", "");
        prefs.putString("name","");
        prefs.putString("mobile","");
        prefs.putString("authorizationToken", "");
        prefs.putBoolean("isLoggedIn", false);
        prefs.putBoolean("isUserSubscribed", false);
        prefs.commit();
    }

    public static String getAuthToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("authorizationToken", "");
    }

    public static String getName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("name", "");
    }

    public static String getEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("email", "");
    }

    public static String getMobile(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("mobile", "");
    }


    public static boolean isLoggedin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }

    public static boolean isUserSubscribed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getBoolean("isUserSubscribed", false);
    }
    public static void setEmail(Context context,String email) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public static void setMobile(Context context,String mob) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mobile", mob);
        editor.apply();
    }
}
