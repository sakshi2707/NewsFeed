package com.shotexplorer.newsfeed.Retrofit;

import com.shotexplorer.newsfeed.Models.News;
import com.shotexplorer.newsfeed.Models.WebSite;
import com.shotexplorer.newsfeed.Utils.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface
{
    //we will define methods here which we want to execute
    @GET("v2/sources?language=en&apiKey=" + AppConstants.API_KEY)
    //we need to have our own model for the websites that's why we are using Call<A>
    //In place of A , we need to use the website model
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);


}
