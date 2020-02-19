package com.example.shoppingmall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shoppingmall.Models.GroceryItem;

import java.util.ArrayList;

public class ShowItemByCategoryActivity extends AppCompatActivity {

    private TextView categoryName;
    private RecyclerView recView;

    private GroceryItemAdapter adapter;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_by_category);

        utils = new Utils(this);
        categoryName = findViewById(R.id.categoryName);
        recView = findViewById(R.id.recView);
        adapter = new GroceryItemAdapter(this);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new GridLayoutManager(this,2));

        try {
            Intent intent = getIntent();
            String category = intent.getStringExtra("category");
            ArrayList<GroceryItem> items = utils.getItemByCategory(category);
            adapter.setItems(items);
            categoryName.setText(category);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
