package com.shotexplorer.newsfeed.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shotexplorer.newsfeed.Adapters.StackAdapter;
import com.shotexplorer.newsfeed.LoginRegister.LoginActivity;
import com.shotexplorer.newsfeed.LoginRegister.ProfileActivity;
import com.shotexplorer.newsfeed.LoginRegister.SharedPrefManager;
import com.shotexplorer.newsfeed.Models.Article;
import com.shotexplorer.newsfeed.Models.News;
import com.shotexplorer.newsfeed.R;
import com.shotexplorer.newsfeed.Retrofit.ApiClient;
import com.shotexplorer.newsfeed.Retrofit.ApiInterface;
import com.shotexplorer.newsfeed.Utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStackActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener {
    @BindView(R.id.swipeStack) SwipeStack swipeStack;
    @BindView(R.id.relativeLayoutNoFound) RelativeLayout relativeLayoutNoFound;
    ApiInterface apiInterface;
    List<Article> arrayListArticles;
    StackAdapter stackAdapter;
    String sourceId,webHotURL;
     News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stack);

        swipeStack=(SwipeStack) findViewById(R.id.swipeStack);
        relativeLayoutNoFound=(RelativeLayout) findViewById(R.id.relativeLayoutNoFound);

        ButterKnife.bind(this);
        init();

        getDataFromPreviousActivity();
    }

    private void getDataFromPreviousActivity() {
        sourceId=getIntent().getStringExtra("sourceId");
        if(!sourceId.isEmpty()){
            loadNews(sourceId);
        }

    }

    private void loadNews(String sourceId) {
        apiInterface.getNewestArticles(ApiClient.getApiUrl(sourceId, AppConstants.API_KEY))
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        news=  response.body();
                        //all the response received is now in this website object



                        //we will check if length of response is empty
                        if(response.isSuccessful() && news.getArticles()!=null){
                            arrayListArticles.clear();
                            webHotURL=news.getArticles().get(0).getUrl();
                            System.out.println("heyy   u"+response.body().getArticles().get(0));
                            arrayListArticles.addAll(news.getArticles());
                            stackAdapter.notifyDataSetChanged();



                        }
                        //else {
                            //Toast.makeText(ListStackActivity.this,"No Articles found",Toast.LENGTH_LONG).show();
                        //}
                        //notifydatasetchanged method is used so that it can refresh the content whenever it changes





                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {
                        Toast.makeText(ListStackActivity.this, "Error:"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void init(){
        //initialize api interface class
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        arrayListArticles=new ArrayList<>();
        stackAdapter=new StackAdapter(arrayListArticles,this);
        swipeStack.setAdapter(stackAdapter);
        swipeStack.setListener(this);

    }

    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {

    }

    @Override
    public void onStackEmpty() {
        swipeStack.setVisibility(View.GONE);
        relativeLayoutNoFound.setVisibility(View.VISIBLE);

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