package com.example.shoppingmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,new MainFragment());
        transaction.commit();
    }
    private void initialize(){
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationDrawer);
        toolbar = findViewById(R.id.toolbar);
      //  frameLayout = findViewById(R.id.fragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.cart:
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.categories:
                ShowAllCategoriesDialog showAllCategoriesDialog = new ShowAllCategoriesDialog();
                showAllCategoriesDialog.show(getSupportFragmentManager(),"All categories");
                break;
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("About")
                        .setMessage("Created by Aksh Shah \n Email: 123akshshah@gmail.com")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.create().show();
                break;
            case R.id.terms:
                AlertDialog.Builder termBuilder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Terms of use")
                        .setMessage("No terms of use \n Thanks for clicking\n Be Curious")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                termBuilder.create().show();

                break;
            case R.id.licenses:
                LicensesDialog licensesDialog = new LicensesDialog();
                licensesDialog.show(getSupportFragmentManager(),"Licenses Dialog");
                break;
            default:
                break;
        }
        return false;
    }
}
