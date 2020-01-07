package com.watson.serendibtravelguide.ui.notifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.ui.search.SearchViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.watson.serendibtravelguide.config.Config.BASE_URL_IMG;

public class RecycleViewAdapterNotification extends RecyclerView.Adapter<RecycleViewAdapterNotification.MyViewHolderSearch> {

    private List<NotificationsViewModel> notVerifiedPlaceList;
    private Context context;

    RecycleViewAdapterNotification(List<NotificationsViewModel> notVerifiedPlaceList, Context context) {
        this.notVerifiedPlaceList = notVerifiedPlaceList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notification_item, parent, false);
        return new MyViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterNotification.MyViewHolderSearch holder, int position) {
        holder.title.setText(notVerifiedPlaceList.get(position).getNotificationMessage());
        holder.name.setText(notVerifiedPlaceList.get(position).getName());
        holder.type.setText(notVerifiedPlaceList.get(position).getType());
        String url =  notVerifiedPlaceList.get(position).getImage();
        url=url.replaceAll(" ","%20");
        Log.d("image path: ","image is in "+BASE_URL_IMG+url);
        url=BASE_URL_IMG+url;

        Glide.with(context).load( url)
                .apply(new RequestOptions().override(50, 50))
                .into(holder.circleImageView);
        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationsFragment.verifyPlace(notVerifiedPlaceList.get(position).getId(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notVerifiedPlaceList.size();
    }

    public class MyViewHolderSearch extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView name;
        private TextView type;
        private ImageButton acceptBtn;
        private CircleImageView circleImageView;

        public MyViewHolderSearch(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_notification_message);
            name = (TextView) itemView.findViewById(R.id.card_notification_place_name);
            type = (TextView) itemView.findViewById(R.id.card_notification_place_type);
            acceptBtn = (ImageButton) itemView.findViewById(R.id.notification_accept_btn);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.place_image_notification);
        }

    }
}
