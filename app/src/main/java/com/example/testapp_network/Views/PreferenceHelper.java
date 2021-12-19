package com.example.testapp_network.Views;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static PreferenceHelper minst;

    private final String AccessToken = "AccessToken";
    private final String User_typeLogin = "User_typeLogin";

    private SharedPreferences app_prefs;
    private static Context mcontext;


    public static synchronized PreferenceHelper getInstans(Context context) {
        if (minst == null) {
            minst = new PreferenceHelper(context);
        }
        return minst;
    }



    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.mcontext = context;
    }

    public void putAccessToken(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(AccessToken, loginorout);
        edit.commit();
    }

    public void putUser_typeLogin(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(User_typeLogin, loginorout);
        edit.commit();
    }

    public String getAccessToken() {
        return app_prefs.getString(AccessToken, "");
    }

    public String getUser_typeLogin() {
        return app_prefs.getString(User_typeLogin, "");
    }


    public boolean logout() {
        app_prefs = mcontext.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = app_prefs.edit();
        editor.clear();
        editor.apply();
        return true;

    }


}
