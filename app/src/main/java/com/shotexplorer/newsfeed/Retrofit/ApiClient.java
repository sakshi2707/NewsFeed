package com.shotexplorer.newsfeed.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//class ApiClient is Singleton class because we have everything static here
public class ApiClient implements Serializable
{
    public static final String BASE_URL="http://newsapi.org";
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient(){
        if(retrofit==null)
        {
            Gson gson =new GsonBuilder()
                    .setLenient()
                    .create();
            //converting retrofit object to gson object
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;

    }
    //method that will return the url of the api
    public static String getApiUrl(String source,String apiKey){
        StringBuilder apiUrl= new StringBuilder("http://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKey)
                .toString();

    }

}
