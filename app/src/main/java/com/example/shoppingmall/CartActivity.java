package com.example.shoppingmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";
    
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Log.d(TAG, "onCreate: Started");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        initBottomNavigation();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,new CartFirstFragment());
        transaction.commit();
    }
    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.cart2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.search:
                        Intent intent1 = new Intent(CartActivity.this,SearchActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        break;
                    case R.id.home_item:
                        Intent intent = new Intent(CartActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
