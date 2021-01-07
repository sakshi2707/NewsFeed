package com.shotexplorer.newsfeed.LoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.shotexplorer.newsfeed.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewUsername,textViewUseremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();//not logged in ,so stop the activity
            startActivity(new Intent(this,LoginActivity.class));
        }

        textViewUseremail=(TextView) findViewById(R.id.textViewEmail);
        textViewUsername=(TextView) findViewById(R.id.textViewUsername);

        textViewUseremail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menuLogout:
//                SharedPrefManager.getInstance(this).logout();
//                finish();
//                startActivity(new Intent(this,LoginActivity.class));
//                break;
//            case R.id.menuProfile:
//                startActivity(new Intent(this,ProfileActivity.class));
//                break;
//        }
//        return true;
//    }
}