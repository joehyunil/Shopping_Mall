package com.example.shoppingmall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.Models.GroceryItem;
import com.example.shoppingmall.Models.Review;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {

    private static final String TAG = "GroceryItemActivity";
    private TextView itemName,itemPrice,itemDesc,itemAvail;
    private ImageView itemImage;
    private Button addCart;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackUserTime.LocalBinder binder =
                    (TrackUserTime.LocalBinder) service;
            mService = binder.getService();
            isBound = true;
            mService.setItem(incomingItem);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    private TrackUserTime mService;
    private Boolean isBound = false;

    private ImageView firstFilledStar,secondFilledStar,thirdFilledStar,firstEmptyStar,secondEmptyStar,thirdEmptyStar;
    private RecyclerView reviewRecView;

    private RelativeLayout reviewLayout;

    private ReviewsAdapter adapter;

    private GroceryItem incomingItem;
    private Utils utils;

    private int currentRate = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        utils = new Utils(this);

        initialize();

        Intent intent = getIntent();
        try {
            incomingItem = intent.getParcelableExtra("item");
            this.currentRate = incomingItem.getRate();
            changeVisibility(currentRate);
            setValues();
            utils.increaseUserPoint(incomingItem,1);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void setValues(){
        itemName.setText(incomingItem.getName());
        itemPrice.setText(String.valueOf(incomingItem.getPrice() + " ₹"));
        itemDesc.setText(incomingItem.getDescription());
        itemAvail.setText(incomingItem.getAvailableAmount() + " number(s) available");

        Glide.with(this)
                .asBitmap()
                .load(incomingItem.getImageUrl())
                .into(itemImage);

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getAvailableAmount() == 0){
                    Toast.makeText(mService, incomingItem.getName() + " is out of stock", Toast.LENGTH_LONG).show();
                }else {
                    utils.addItemToCart(incomingItem.getId());
                    Intent intent = new Intent(GroceryItemActivity.this,CartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        handleStar();

        adapter = new ReviewsAdapter();
        reviewRecView.setAdapter(adapter);
        reviewRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Review> reviews = utils.getReviewForItem(incomingItem.getId());
        if(null != reviews){
            adapter.setReviews(reviews);
        }

        reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReviewDialog addReviewDialog = new AddReviewDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("item",incomingItem);
                addReviewDialog.setArguments(bundle);
                addReviewDialog.show(getSupportFragmentManager(),"Add Review Dialog");
            }
        });
    }

    private void handleStar(){

        firstEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(checkIfRateHasChanged(1)){
                   updateDatabase(1);
                   changeVisibility(1);
                   changeUserPoint(1);
               }
            }
        });

        secondEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(2)){
                    updateDatabase(2);
                    changeVisibility(2);
                    changeUserPoint(2);
                }
            }
        });

        thirdEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(3)){
                    updateDatabase(3);
                    changeVisibility(3);
                    changeUserPoint(3);
                }
            }
        });

        firstFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(1)){
                    updateDatabase(1);
                    changeVisibility(1);
                    changeUserPoint(1);
                }
            }
        });

        secondFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(2)){
                    updateDatabase(2);
                    changeVisibility(2);
                    changeUserPoint(2);
                }
            }
        });

        thirdFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(3)){
                    updateDatabase(3);
                    changeVisibility(3);
                    changeUserPoint(3);
                }
            }
        });
    }

    private void changeUserPoint(int stars){

        utils.increaseUserPoint(incomingItem,(stars-currentRate)*2);
    }

    private void updateDatabase(int newRate){
        utils.updateTheRate(incomingItem,newRate);
    }

    private void changeVisibility(int newRate){
        switch (newRate){
            case 0:
                firstFilledStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 1:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 2:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 3:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.GONE);
                break;
            default:
                firstFilledStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean checkIfRateHasChanged(int newRate){
        if(newRate == currentRate){
            return false;
        }
        else {
            return true;
        }
    }

    private void initialize(){
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        itemDesc = findViewById(R.id.itemDesc);
        itemAvail = findViewById(R.id.itemAvail);

        itemImage = findViewById(R.id.itemImage);

        addCart = findViewById(R.id.addCart);

        firstEmptyStar = findViewById(R.id.firstEmptyStar);
        firstFilledStar = findViewById(R.id.firstFilledStar);
        secondEmptyStar = findViewById(R.id.secondEmptyStar);
        secondFilledStar = findViewById(R.id.secondFilledStar);
        thirdEmptyStar = findViewById(R.id.thirdEmptyStar);
        thirdFilledStar = findViewById(R.id.thirdFilledStar);

        reviewRecView = findViewById(R.id.reviewRecView);

        reviewLayout = findViewById(R.id.reviewLayout);
    }

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: " + review.toString());
        utils.Add_Review(review);
        utils.increaseUserPoint(incomingItem,3);
        ArrayList<Review> reviews = utils.getReviewForItem(review.getGroceryItemId());
        if(null != reviews){
            adapter.setReviews(reviews);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this,TrackUserTime.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(isBound){
            unbindService(connection);
        }
    }
}
