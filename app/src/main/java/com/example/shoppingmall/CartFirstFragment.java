package com.example.shoppingmall;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmall.Models.GroceryItem;
import com.example.shoppingmall.Models.Order;

import java.util.ArrayList;

public class CartFirstFragment extends Fragment implements CartRecViewAdapter.GetTotalPrice,
        CartRecViewAdapter.DeleteCartItem {
    private static final String TAG = "CartFirstFragment";

    @Override
    public void onGettingTotalPriceResult(double price) {
        txtSum.setText(String.valueOf(price));
        this.totalPrice = price;
    }

    @Override
    public void onDeletingResult(GroceryItem item) {
        ArrayList<Integer> itemIds = new ArrayList<>();
        itemIds.add(item.getId());

        ArrayList<GroceryItem> items = utils.getItemsById(itemIds);
        if(items.size()>0){

            ArrayList<Integer> newItemIds = utils.deleteCartItem(items.get(0));
            if (newItemIds.size()==0) {
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
                recView.setVisibility(View.GONE);
                txtNoItem.setVisibility(View.VISIBLE);

            }else {
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setEnabled(true);
                recView.setVisibility(View.VISIBLE);
                txtNoItem.setVisibility(View.GONE);
            }

            ArrayList<GroceryItem> newItems = utils.getItemsById(newItemIds);
            this.items = newItemIds;
            adapter.setItems(newItems);

        }

    }

    private TextView txtSum,txtNoItem;
    private RecyclerView recView;
    private Button btnNext;
    private RelativeLayout relLayout;

    private CartRecViewAdapter adapter;

    private double totalPrice = 0;
    private ArrayList<Integer> items;

    private Utils utils;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Started");
        
        View view = inflater.inflate(R.layout.fragment_first_cart,container,false);

        initViews(view);
        items = new ArrayList<>();

        utils = new Utils(getActivity());

        initRecView();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                order.setTotalPrice(totalPrice);
                order.setItems(items);
                Bundle bundle = new Bundle();
                bundle.putParcelable("order",order);
                CartSecondFragment cartSecondFragment =  new CartSecondFragment();
                cartSecondFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,cartSecondFragment).commit();
            }
        });

        return view;
    }

    private void initRecView(){
        adapter = new CartRecViewAdapter(this);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Utils utils = new Utils(getActivity());

        ArrayList<Integer> itemIds = utils.getCartItems();
        if(null != itemIds) {

            ArrayList<GroceryItem> items = utils.getItemsById(itemIds);

            if (items.size() == 0) {
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
                recView.setVisibility(View.GONE);
                txtNoItem.setVisibility(View.VISIBLE);
                relLayout.setVisibility(View.GONE);
            }
            this.items = itemIds;
            adapter.setItems(items);

        }else {
            btnNext.setVisibility(View.GONE);
            btnNext.setEnabled(false);
            recView.setVisibility(View.GONE);
            txtNoItem.setVisibility(View.VISIBLE);
            relLayout.setVisibility(View.GONE);
        }
    }

    private void initViews(View view){
        Log.d(TAG, "initViews: Started");
        txtSum = view.findViewById(R.id.txtSum);
        txtNoItem = view.findViewById(R.id.txtNoItem);
        btnNext = view.findViewById(R.id.btnNext);
        relLayout = view.findViewById(R.id.relLayout);
        recView = view.findViewById(R.id.recView);
    }


}

