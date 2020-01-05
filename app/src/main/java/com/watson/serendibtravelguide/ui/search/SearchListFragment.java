package com.watson.serendibtravelguide.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.watson.serendibtravelguide.R;

import java.util.ArrayList;
import java.util.List;

public class SearchListFragment extends Fragment {
    private SearchViewModel searchViewModel;
    ArrayAdapter<String> adapter;
    List<SearchViewModel> test_search_results = new ArrayList<>();
    RecyclerView recyclerView;
    RecycleViewAdapterSearch searchAdapter;

    public SearchListFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search_list, container, false);

        test_search_results.add(new SearchViewModel("search1","tag1"));
        test_search_results.add(new SearchViewModel("search2","tag1,2"));

        recyclerView = root.findViewById(R.id.search_result_list);
        searchAdapter = new RecycleViewAdapterSearch(test_search_results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);
        return root;
    }




}
