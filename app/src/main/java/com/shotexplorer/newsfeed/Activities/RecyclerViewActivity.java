package com.shotexplorer.newsfeed.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shotexplorer.newsfeed.Adapters.SourcesAdapter;
import com.shotexplorer.newsfeed.LoginRegister.LoginActivity;
import com.shotexplorer.newsfeed.LoginRegister.ProfileActivity;
import com.shotexplorer.newsfeed.LoginRegister.SharedPrefManager;
import com.shotexplorer.newsfeed.Models.Source;
import com.shotexplorer.newsfeed.Models.WebSite;
import com.shotexplorer.newsfeed.R;
import com.shotexplorer.newsfeed.Retrofit.ApiClient;
import com.shotexplorer.newsfeed.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewActivity extends AppCompatActivity {
    public AlertDialog dialog;


    @BindView(R.id.recyclerViewMainActivity) public RecyclerView recyclerView;

    //initialize our interface
    ApiInterface apiInterface;
    List<Source> listSources;
    SourcesAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMainActivity);
        ButterKnife.bind(this);
        init();
        loadWebsiteSources();

    }
//this method will load all the sources from the url
    private void loadWebsiteSources() {
            dialog.show();

        apiInterface.getSources().enqueue(new Callback<WebSite>() {
            @Override
            public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                dialog.dismiss();

                //here we are going to receive the response, we can receive the response and
                // store it in our model class website
                WebSite webSite=response.body();
                //all the response received is now in this website object



                //we will check if length of response is empty
                if(webSite!=null && response.body().getSources().size()>0){
                    //firstly clear the arraylist and
                    listSources.clear();
                    System.out.print("sucesssssss  "+response.isSuccessful());
                    //add this response data to our Arraylist
                    listSources.addAll(webSite.getSources());

                }
                else {
                    Toast.makeText(RecyclerViewActivity.this,"No sources found",Toast.LENGTH_LONG).show();
                }
                //notifydatasetchanged method is used so that it can refresh the content whenever it changes
                //and easily display the data
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<WebSite> call, Throwable t) {
                Toast.makeText(RecyclerViewActivity.this,"Error:"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }


        });

    }

    private void init() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        dialog=new SpotsDialog.Builder().setContext(this).build();

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        listSources=new ArrayList<>();
        adapter=new SourcesAdapter(this,listSources);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.menuProfile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
        return true;
    }


}