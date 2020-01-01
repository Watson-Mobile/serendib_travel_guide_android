package com.watson.serendibtravelguide.ui.home;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.watson.serendibtravelguide.ui.places.DetailedDestination;
import com.watson.serendibtravelguide.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<CardViewModel> movieList;

    RecyclerViewAdapter(List<CardViewModel> movieList){
        this.movieList = movieList;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.title.setText(movieList.get(position).getTitle());
        holder.image.setBackgroundResource(movieList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            image = (ImageView)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            CardViewModel currentPlace =  view.get(this.getAdapterPosition());
            Intent detailIntent = new Intent(itemView.getContext(), DetailedDestination.class);
//            detailIntent.putExtra("title", currentSport.getTitle());
//            detailIntent.putExtra("image_resource", currentSport.getImageResource());
            detailIntent.putExtra("title","SIGIRIYA");
            detailIntent.putExtra("description","Add the word Detail to every " +
                    "reference to an id, in order to differentiateit from list_item ids. " +
                    "For example, change the ImageView ID from sportsImage to sportsImageDetail, " +
                    "as well as any references to this ID for relative placement such " +
                    "layout_below.");
//            detailIntent.putExtra("image_resource",image);
            itemView.getContext().startActivity(detailIntent);

        }
    }
}