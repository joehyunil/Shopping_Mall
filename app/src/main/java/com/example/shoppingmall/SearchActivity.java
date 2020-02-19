package com.example.shoppingmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall.Models.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ShowAllCategoriesDialog.SelectCategory{

    private EditText editSearch;
    private TextView firstCat,secondCat,thirdCat,seeAll;
    private RecyclerView recView;
    private BottomNavigationView bottomNavigationView;
    private ImageView btnSearch;

    private GroceryItemAdapter adapter;

    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        utils = new Utils(this);
        adapter = new GroceryItemAdapter(this);

        initialize();

        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        initBottomNavigation();
        initTextViews();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch();
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllCategoriesDialog showAllCategoriesDialog = new ShowAllCategoriesDialog();
                showAllCategoriesDialog.show(getSupportFragmentManager(),"All Categories");
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<GroceryItem> items = utils.searchItem(String.valueOf(s));
                adapter.setItems(items);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initTextViews(){
        ArrayList<String> categories = utils.getCategories();
        switch (categories.size()){
            case 1:
                firstCat.setText(categories.get(0));
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                break;
            case 2:
                firstCat.setText(categories.get(0));
                secondCat.setText(categories.get(1));
                thirdCat.setVisibility(View.GONE);
                break;
            case 3:
                firstCat.setText(categories.get(0));
                secondCat.setText(categories.get(1));
                thirdCat.setText(categories.get(2));
                break;
            default:
                break;
        }

        firstCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,ShowItemByCategoryActivity.class);
                intent.putExtra("category",firstCat.getText().toString());
                startActivity(intent);
            }
        });

        secondCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,ShowItemByCategoryActivity.class);
                intent.putExtra("category",secondCat.getText().toString());
                startActivity(intent);
            }
        });

        thirdCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,ShowItemByCategoryActivity.class);
                intent.putExtra("category",thirdCat.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void initiateSearch(){

        String text = editSearch.getText().toString();
        ArrayList<GroceryItem> items = utils.searchItem(text);
        for(GroceryItem item:items){
            utils.increaseUserPoint(item,3);
        }
        adapter.setItems(items);
    }

    private void initialize(){
        editSearch = findViewById(R.id.editSearch);
        firstCat = findViewById(R.id.firstCategory);
        secondCat = findViewById(R.id.secondCategory);
        thirdCat = findViewById(R.id.thirdCategory);
        seeAll = findViewById(R.id.seeAll);
        recView = findViewById(R.id.recView);
        btnSearch = findViewById(R.id.btnSearch);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.search:
                        break;
                    case R.id.home_item:
                        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart2:
                        Intent intent1 = new Intent(SearchActivity.this,CartActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onSelectCategory(String category) {
        Intent intent = new Intent(SearchActivity.this,ShowItemByCategoryActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
