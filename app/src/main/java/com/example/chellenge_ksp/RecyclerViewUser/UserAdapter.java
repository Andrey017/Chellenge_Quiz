package com.example.chellenge_ksp.RecyclerViewUser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chellenge_ksp.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<Item> itemArrayList;

    public UserAdapter (ArrayList<Item> itemArray){
        itemArrayList = itemArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = itemArrayList.get(position);

        holder.utextView.setText(currentItem.getNameUser());
        holder.uimageView.setImageResource(currentItem.getImageResource());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView utextView;
        public ImageView uimageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            utextView = itemView.findViewById(R.id.textView_row);
            uimageView = itemView.findViewById(R.id.imageView_row);
        }
    }
}
