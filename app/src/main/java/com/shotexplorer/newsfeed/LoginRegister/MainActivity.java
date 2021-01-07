package com.shotexplorer.newsfeed.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextPassword,editTextUsername,editTextEmail;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, RecyclerViewActivity.class));
            return;
        }


        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        editTextUsername=(EditText) findViewById(R.id.editTextUsername);

        textViewLogin=(TextView) findViewById(R.id.textViewLogin);

        buttonRegister=(Button) findViewById(R.id.buttonRegister);
        progressDialog=new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }
    private void registerUser(){
        final String email=editTextEmail.getText().toString().trim();
        final String username=editTextUsername.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        progressDialog.setMessage("Registering user.....");
        progressDialog.show();


        //we will create a request method here ,for that
        // ,we need a string request so it is defined under volley

        //string request constructor will take first parameter as string method so as we are inserting
        //the values, we are using POST so we will define request dot method dot post
        //the second parameter will be the URL, where we want to send the request
        //third parameter will be the response listener & 4th will be error listener
        //we also need to add parameters to this string request & the parameters will be containing the values
        //to be inserted in the database


        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //we will pass the json response
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();} catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // we have overriden a method getparams where we will put all the parameters that need to be
                //sent in a hashmap
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return params;
                //we have attached parameters
            }
        };
        //we will add stringRequest to requestQueue object
        //firstly we used this below 2 lines but most efficient is requesthandler
       // RequestQueue requestQueue= Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onClick(View view) {
        if(view==buttonRegister){
            registerUser();
        }
        if(view==textViewLogin){
            startActivity(new Intent(this,LoginActivity.class));

        }

    }
}