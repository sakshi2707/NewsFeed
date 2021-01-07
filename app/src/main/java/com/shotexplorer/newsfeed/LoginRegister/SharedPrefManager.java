package com.shotexplorer.newsfeed.LoginRegister;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    //here we don't need request queue
    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USERNAME="username";
    private static final String KEY_USER_EMAIL="useremail";
    private static final String KEY_USER_ID="userid";

    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }
    public boolean userLogin(int id,String username,String email){
        //we need to store id,username,email & not password
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //mode private means only this application can access this shared preference
        SharedPreferences.Editor editor=sharedPreferences.edit();

        //now we will put all the three parameters in the editor
        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);

        editor.apply();//apply the changes on the editor
        return true;
    }
    public boolean isLoggedIn(){
        //we will used sharedPref to check this and here we don't need editor
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME,null)!=null){
            return true;
        }return false;

    }
    public boolean logout(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();//removes all the values from editor
        editor.apply();//and save it
        return true;
    }
    public String getUsername(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);


    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);

    }



}