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
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public GameAdapter(ArrayList<ItemGame> itemGames){
        itemGameArrayList = itemGames;
    }

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_game, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder holder, int position) {
        ItemGame currentItem = itemGameArrayList.get(position);

        holder.gtextView.setText(currentItem.getName_game());
        //holder.gimageView.setImageResource(currentItem.getImageResource());
    }

    @Override
    public int getItemCount() {
        return itemGameArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gtextView;
        private ImageView gimageView;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            gtextView = itemView.findViewById(R.id.textView_row_game);
            //gimageView = itemView.findViewById(R.id.imageView_row_game);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
