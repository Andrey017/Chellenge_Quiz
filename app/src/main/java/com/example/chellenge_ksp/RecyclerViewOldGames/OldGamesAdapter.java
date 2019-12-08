package com.example.chellenge_ksp.RecyclerViewOldGames;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chellenge_ksp.R;

import java.util.ArrayList;

public class OldGamesAdapter extends RecyclerView.Adapter<OldGamesAdapter.ViewHolder> {
    private ArrayList<ItemOldGames> itemOldGamesArrayList;

    public OldGamesAdapter(ArrayList<ItemOldGames> itemOldGames){
        itemOldGamesArrayList = itemOldGames;
    }

    @NonNull
    @Override
    public OldGamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_old_games, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OldGamesAdapter.ViewHolder holder, int position) {
        ItemOldGames currentItem = itemOldGamesArrayList.get(position);

        holder.textView.setText(currentItem.getName_game());
    }

    @Override
    public int getItemCount() {
        return itemOldGamesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView_row_old_game);
        }
    }
}
