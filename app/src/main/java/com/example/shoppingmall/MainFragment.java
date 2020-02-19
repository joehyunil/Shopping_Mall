package com.example.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmall.Models.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ServiceConfigurationError;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemRecView,popItemRecView,sugItemRecView;
    private GroceryItemAdapter newItemAdapter,popItemAdapter,sugItemAdapter;

    private Utils utils;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        initViews(view);
        initBottomNavigation();

        utils = new Utils(getActivity());
        utils.initDatabase();

        initRecViews();

        return view;
    }

    private void initRecViews(){
        newItemAdapter = new GroceryItemAdapter(getActivity());
        popItemAdapter = new GroceryItemAdapter(getActivity());
        sugItemAdapter = new GroceryItemAdapter(getActivity());

        newItemRecView.setAdapter(newItemAdapter);
        popItemRecView.setAdapter(popItemAdapter);
        sugItemRecView.setAdapter(sugItemAdapter);

        newItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        sugItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        updateRecViews();
    }

    private void updateRecViews(){
        ArrayList<GroceryItem> newItems = utils.getAllItems();
        Comparator<GroceryItem> newItemComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o1.getId() - o2.getId();
            }
        };
        Comparator<GroceryItem> revNewItemComparator = Collections.reverseOrder(newItemComparator);
        Collections.sort(newItems,revNewItemComparator);
        if(null != newItems){
            newItemAdapter.setItems(newItems);
        }


        ArrayList<GroceryItem> popItems = utils.getAllItems();
        Comparator<GroceryItem> popularComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return compareByPopularity(o1, o2);
            }
        };
        Comparator<GroceryItem> revPopComparator =  Collections.reverseOrder(popularComparator);
        Collections.sort(popItems,revPopComparator);
        popItemAdapter.setItems(popItems);


        ArrayList<GroceryItem> suggestedItems = utils.getAllItems();
        Comparator<GroceryItem> suggestedComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o1.getUserPoint() - o2.getUserPoint();
            }
        };
        Comparator<GroceryItem> revSugComparator = Collections.reverseOrder(suggestedComparator);
        Collections.sort(suggestedItems,revSugComparator);
        sugItemAdapter.setItems(suggestedItems);
    }

    @Override
    public void onResume() {
        updateRecViews();
        super.onResume();
    }

    private int compareByPopularity (GroceryItem item1, GroceryItem item2){
        if(item1.getPopularityPoint() > item2.getPopularityPoint()){
            return 1;
        }else if(item1.getPopularityPoint()< item2.getPopularityPoint()){
            return -1;
        }else{
            return 0;
        }
    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.home_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.home_item:
                        break;
                    case R.id.cart2:
                        Intent intent1 = new Intent(getActivity(),CartActivity.class);
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

    private void initViews(View view){
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        newItemRecView = view.findViewById(R.id.newItemRecView);
        popItemRecView = view.findViewById(R.id.popItemRecView);
        sugItemRecView = view.findViewById(R.id.sugItemRecView);
    }

}
