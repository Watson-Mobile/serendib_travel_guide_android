package com.watson.serendibtravelguide.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
import com.watson.serendibtravelguide.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.watson.serendibtravelguide.config.Config.BASE_URL_IMG;

public class RecycleViewAdapterSearch extends RecyclerView.Adapter<RecycleViewAdapterSearch.MyViewHolderSearch> {

    private List<SearchViewModel> placeList;
    private Context context;

    RecycleViewAdapterSearch(List<SearchViewModel> placeList ,Context context){
        this.placeList = placeList;
        this.context=context;
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
        holder.otherNames.setText(placeList.get(position).getOtherNames());
        holder.tag.setText(placeList.get(position).getCardTag(), TextView.BufferType.NORMAL);
        Glide.with(context).load(BASE_URL_IMG+placeList.get(position).getImage())
                .apply(new RequestOptions().override(100, 100))
                .into(holder.circularPlaceImage);
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class MyViewHolderSearch extends RecyclerView.ViewHolder {

        private TextView title;
        private Chip tag;
        private TextView otherNames;
        private CircleImageView circularPlaceImage;

        public MyViewHolderSearch(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.card_title_search);
            otherNames = (TextView)itemView.findViewById(R.id.card_secondary_title_search);
            tag = (Chip) itemView.findViewById(R.id.item_chip_tag);
            circularPlaceImage =  (CircleImageView) itemView.findViewById(R.id.place_image);
        }

    }
}
