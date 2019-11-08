package com.example.chellenge_ksp.RecyclerViewGame;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chellenge_ksp.R;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private ArrayList<ItemGame> itemGameArrayList;

    public GameAdapter(ArrayList<ItemGame> itemGames){
        itemGameArrayList = itemGames;
    }

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_game, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder holder, int position) {
        ItemGame currentItem = itemGameArrayList.get(position);

        holder.gtextView.setText(currentItem.getName_game());
        holder.gimageView.setImageResource(currentItem.getImageResource());
    }

    @Override
    public int getItemCount() {
        return itemGameArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gtextView;
        private ImageView gimageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gtextView = itemView.findViewById(R.id.textView_row_game);
            gimageView = itemView.findViewById(R.id.imageView_row_game);
        }
    }
}
