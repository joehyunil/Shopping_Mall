package com.example.shoppingmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmall.Models.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{

    private Context context;

    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Review> reviews = new ArrayList<>();

    public ReviewsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_adapter_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(reviews.get(position).getUsername());
        holder.reviewText.setText(reviews.get(position).getText());
        holder.date.setText(reviews.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView username,reviewText,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            reviewText = itemView.findViewById(R.id.reviewText);
        }
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
