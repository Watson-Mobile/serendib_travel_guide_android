package com.watson.serendibtravelguide.ui.home;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.ui.Utils.ImageUrl;
import com.watson.serendibtravelguide.ui.places.DetailedDestination;
import com.watson.serendibtravelguide.R;

import java.util.ArrayList;
import java.util.List;

import static com.watson.serendibtravelguide.config.Config.BASE_URL_IMG;
import static com.watson.serendibtravelguide.ui.home.HomeFragment.getPlaceList1;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<CardViewModel> suggestedPlaceList;
    private List<Place> placeList;
    private ArrayList<ImageUrl> imageUrls;
    private Context context;


    RecyclerViewAdapter(List<CardViewModel> suggestedPlaceList, Context context ){
        this.suggestedPlaceList = suggestedPlaceList;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card,parent,false);
        placeList = getPlaceList1();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.title.setText(suggestedPlaceList.get(position).getTitle());
//        holder.image.setBackgroundResource(suggestedPlaceList.get(position).getImage());
        holder.type.setText(suggestedPlaceList.get(position).getType());
        holder.distance.setText(suggestedPlaceList.get(position).getDistance());

        Glide.with(context).load(BASE_URL_IMG+placeList.get(position).getImagePaths().get(0))
                .apply(new RequestOptions().override(200, 200))
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DetailedDestination.class);
                intent.putExtra("title",placeList.get(position).getName());
                intent.putExtra("description",placeList.get(position).getDescription());
                intent.putExtra("imagePath",placeList.get(position).getImagePaths().get(0));
                intent.putExtra("location_point",placeList.get(position).getLocation());

                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return suggestedPlaceList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private Chip distance;
        private Chip type;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.card_title);
            image = (ImageView)itemView.findViewById(R.id.card_image);
            distance = (Chip) itemView.findViewById(R.id.item_chip_distance);
            type = (Chip) itemView.findViewById(R.id.item_chip_tag);
//
        }

//        @Override
//        public void onClick(View view) {
////            CardViewModel currentPlace =  view.get(this.getAdapterPosition());
//            Intent detailIntent = new Intent(itemView.getContext(), DetailedDestination.class);
////            detailIntent.putExtra("title", currentSport.getTitle());
////            detailIntent.putExtra("image_resource", currentSport.getImageResource());
//            detailIntent.putExtra("title","SIGIRIYA");
//            detailIntent.putExtra("description","Add the word Detail to every " +
//                    "reference to an id, in order to differentiateit from list_item ids. " +
//                    "For example, change the ImageView ID from sportsImage to sportsImageDetail, " +
//                    "as well as any references to this ID for relative placement such " +
//                    "layout_below.");
////            detailIntent.putExtra("image_resource",image);
//            itemView.getContext().startActivity(detailIntent);
//
//        }
    }
}