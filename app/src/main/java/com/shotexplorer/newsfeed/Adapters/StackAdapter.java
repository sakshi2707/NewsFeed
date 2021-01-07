package com.shotexplorer.newsfeed.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.shotexplorer.newsfeed.Models.Article;
import com.shotexplorer.newsfeed.R;
import com.shotexplorer.newsfeed.Utils.AppConstants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class StackAdapter extends BaseAdapter {

    private List<Article> arrayListArticles;
    private Context context;

    public StackAdapter(List<Article> arrayListArticles,Context context) {
        this.arrayListArticles = arrayListArticles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListArticles.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListArticles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.card_layout,parent,false);
        }
        final ProgressBar progressBar=(ProgressBar) convertView.findViewById(R.id.progressBar);
        TextView textViewTitle=(TextView) convertView.findViewById(R.id.newsTitle);
        TextView textViewDescription=(TextView) convertView.findViewById(R.id.newsDescription);
        TextView textViewUrl=(TextView) convertView.findViewById(R.id.newsUrl);
        TextView textViewAuthor=(TextView) convertView.findViewById(R.id.websiteNameTxt);
        ImageView imageViewNews=(ImageView) convertView.findViewById(R.id.imageNews);
        ImageView imageViewShare=(ImageView) convertView.findViewById(R.id.shareNews);

        RelativeTimeTextView textViewTime=(RelativeTimeTextView) convertView.findViewById(R.id.newsTime);

        if(arrayListArticles.get(position).getUrlToImage()!=null && arrayListArticles.get(position).getUrlToImage().length()>0){
            Picasso.get().load(arrayListArticles.get(position).getUrlToImage())
                    .into(imageViewNews, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }
        if(arrayListArticles.get(position).getAuthor()==null){textViewAuthor.setVisibility(View.GONE);
        }
        else if(arrayListArticles.get(position).getAuthor().length()>17){
            textViewAuthor.setVisibility(View.VISIBLE);
            textViewAuthor.setText(arrayListArticles.get(position).getAuthor().substring(0,17)+"..");}
        else{
            textViewAuthor.setVisibility(View.VISIBLE);
            textViewAuthor.setText(arrayListArticles.get(position).getAuthor());
        }

        if(arrayListArticles.get(position).getTitle().length()>81){
            textViewTitle.setText(arrayListArticles.get(position).getTitle().substring(0,81)+"...");

        }else { textViewTitle.setText(arrayListArticles.get(position).getTitle());

        }
        if(arrayListArticles.get(position).getPublishedAt()!=null){
            Date date=null;
            try{
                date= AppConstants.parse(arrayListArticles.get(position).getPublishedAt());
                textViewTime.setReferenceTime(date.getTime());

            }catch(Exception e){
                e.printStackTrace();
            }

        }

        textViewDescription.setText(arrayListArticles.get(position).getDescription());
        textViewUrl.setText(arrayListArticles.get(position).getUrl());

        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent=new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Checkout this news from my NEWSFEED App :- "+
                        arrayListArticles.get(position).getTitle() +
                        " : "+ arrayListArticles.get(position).getUrl());
                context.startActivity(Intent.createChooser(sharingIntent,context.getString(R.string.share_article)));
               // Toast.makeText(context,"Share this news with your friends",Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
