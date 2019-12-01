package com.example.chellenge_ksp.RecyclerViewResult;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chellenge_ksp.R;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private ArrayList<ItemResult> itemResultArrayList;

    public ResultAdapter(ArrayList<ItemResult> itemResults){
        itemResultArrayList = itemResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemResult currentItem = itemResultArrayList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView_name.setText(currentItem.getName_user());
        holder.textView_points.setText(currentItem.getPoints());
    }

    @Override
    public int getItemCount() {
        return itemResultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView_name;
        private TextView textView_points;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_row_result);
            textView_name = itemView.findViewById(R.id.textView_row_result_name);
            textView_points = itemView.findViewById(R.id.textView_row_result_count);
        }
    }
}
