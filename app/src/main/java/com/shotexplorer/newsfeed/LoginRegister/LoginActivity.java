package com.shotexplorer.newsfeed.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shotexplorer.newsfeed.Activities.RecyclerViewActivity;
import com.shotexplorer.newsfeed.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextUsername,editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    private TextView textViewRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        editTextUsername=(EditText) findViewById(R.id.editTextUsername);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        buttonLogin=(Button) findViewById(R.id.buttonLogin);
        textViewRegister=(TextView) findViewById(R.id.textViewRegister);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }
    private void userLogin(){
        final String username=editTextUsername.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj=new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(obj.getInt("id"),
                                                   obj.getString("username"),
                                                   obj.getString("email"));
                                startActivity(new Intent(getApplicationContext(), RecyclerViewActivity.class));
                                finish();//so that when the user presses back, we should not return to login activity

                            }else{
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();


            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogin){
            userLogin();
        }
        if(view==textViewRegister){
            startActivity(new Intent(this,MainActivity.class));

        }
    }
}