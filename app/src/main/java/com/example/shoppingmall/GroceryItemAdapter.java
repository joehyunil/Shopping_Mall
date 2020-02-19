package com.example.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.Models.GroceryItem;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GroceryItem> items = new ArrayList<>();

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    public GroceryItemAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_rec_view_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemName.setText(items.get(position).getName());
        holder.itemPrice.setText(String.valueOf(items.get(position).getPrice() + " ₹"));
        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageUrl())
                .into(holder.itemImage);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GroceryItemActivity.class);
                intent.putExtra("item",items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private TextView itemPrice,itemName;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemName = itemView.findViewById(R.id.itemName);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
