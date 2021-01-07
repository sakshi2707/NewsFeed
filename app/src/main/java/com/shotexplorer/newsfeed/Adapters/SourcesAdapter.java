package com.shotexplorer.newsfeed.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shotexplorer.newsfeed.Activities.ListStackActivity;
import com.shotexplorer.newsfeed.Models.Source;
import com.shotexplorer.newsfeed.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


//we will send context of the activity and arraylist to the adapter and we need to bind it to recycler view
public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder> {


    Context context;
    List<Source> sourceList;
    public SourcesAdapter(Context context, List<Source> sourceList){
        this.context=context;
        this.sourceList=sourceList;

    }
    @NonNull
    @Override
    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.item_source,viewGroup,false);
        return new SourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourcesViewHolder sourcesViewHolder, int i) {
        //System.out.println("Hey Sakshi,i passed thisssss");
        //String text=sourceList.get(i).getName().toString();
        //sourcesViewHolder.textViewName.setText("hhhhhhh");
        sourcesViewHolder.textViewName.setText(sourceList.get(i).getName());
        System.out.println(sourceList.get(i).getName());
        //System.out.println("Hey Sakshi,i passed thisssss tooo");

    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    public class SourcesViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.source_image) CircleImageView circleImageView;


        @BindView(R.id.source_name) TextView textViewName;



        public SourcesViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=(CircleImageView) itemView.findViewById(R.id.source_image);
            textViewName=(TextView) itemView.findViewById(R.id.source_name);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //we need to fetch the object of Source from arrayList
                    Source sourceItem=sourceList.get(getBindingAdapterPosition());
                    System.out.println("pos: "+sourceList.get(getAbsoluteAdapterPosition()));
                    //we need to find id of the datasource because we will need the id in API key
                    //here we are just sending the ID to our next activity & we can find ID from
                    //sourceItem object
                    Intent intent=new Intent(context, ListStackActivity.class);
                    intent.putExtra("sourceId",sourceItem.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });


        }

    }


}
