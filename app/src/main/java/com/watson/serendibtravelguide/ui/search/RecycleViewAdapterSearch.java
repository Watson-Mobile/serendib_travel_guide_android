package com.watson.serendibtravelguide.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.models.Place;

import java.util.List;

public class RecycleViewAdapterSearch extends RecyclerView.Adapter<RecycleViewAdapterSearch.MyViewHolderSearch> {

    private List<SearchViewModel> placeList;

    RecycleViewAdapterSearch(List<SearchViewModel> movieList ){
        this.placeList = movieList;
    }


    @NonNull
    @Override
    public MyViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_list_item,parent,false);
        return new MyViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterSearch.MyViewHolderSearch holder, int position) {
        holder.title.setText(placeList.get(position).getCardTitle());
        holder.distance.setText(placeList.get(position).getCardTag());


    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class MyViewHolderSearch extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView distance;

        public MyViewHolderSearch(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.card_title_search);
            distance = (TextView)itemView.findViewById(R.id.card_secondary_title_search);
//            itemView.setOnClickListener(this);
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
