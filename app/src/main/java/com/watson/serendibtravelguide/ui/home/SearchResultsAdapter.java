//package com.watson.serendibtravelguide.ui.home;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.watson.serendibtravelguide.R;
//
//
//public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder>  {
//private String[] mDataset;
//
//public static class MyViewHolder extends RecyclerView.ViewHolder {
//    // each data item is just a string in this case
//    public CardView cardView;
//    public MyViewHolder(CardView v) {
//        super(v);
//        cardView = v;
//    }
//}
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public SearchResultsAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public SearchResultsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                                int viewType) {
//        CardView v = (CardView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.adapter_card, parent, false);
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//       // holder.cardView.
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }
//}
