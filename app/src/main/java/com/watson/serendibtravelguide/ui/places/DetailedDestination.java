package com.watson.serendibtravelguide.ui.places;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.watson.serendibtravelguide.R;

public class DetailedDestination extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_destination);

        TextView placeTitle = (TextView)findViewById(R.id.title_detailed);
        TextView description = (TextView)findViewById(R.id.txt_detailed_description);
        ImageView detailImage = (ImageView)findViewById(R.id.image_detailed);

        placeTitle.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));

//        Glide.with(this).load(getIntent().getIntExtra("image_resource",0))
//                .into(detailImage);

    }
}
