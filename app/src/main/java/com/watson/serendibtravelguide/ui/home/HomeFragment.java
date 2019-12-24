package com.watson.serendibtravelguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.watson.serendibtravelguide.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<CardViewModel> movieList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        movieList = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(movieList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        prepareMovie();

        return root;
    }

    private void prepareMovie(){
        CardViewModel movie = new CardViewModel("Star Wars The Last Jedi",R.drawable.sri_lanka_main);
        movieList.add(movie);
        movie = new CardViewModel("Coco",R.drawable.sri_lanka_main);
        movieList.add(movie);
        movie = new CardViewModel("Justice League ",R.drawable.sri_lanka_main);
        movieList.add(movie);
        movie = new CardViewModel("Thor: Ragnarok",R.drawable.sri_lanka_main);
        movieList.add(movie);
        recyclerViewAdapter.notifyDataSetChanged();
    }

}