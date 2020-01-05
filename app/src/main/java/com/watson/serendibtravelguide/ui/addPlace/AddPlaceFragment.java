package com.watson.serendibtravelguide.ui.addPlace;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.ui.home.HomeActivity;

public class AddPlaceFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_new_place, container, false);
        final TextView textView = root.findViewById(R.id.firstname);
        final Button btnNext = root.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit data or take photograph
                Intent intent = new Intent(v.getContext(), HomeActivity.class);

                v.getContext().startActivity(intent);
            }
        });



        return root;
    }
}
