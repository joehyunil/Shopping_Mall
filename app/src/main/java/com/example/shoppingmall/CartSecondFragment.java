package com.example.shoppingmall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.shoppingmall.Models.Order;

public class CartSecondFragment extends Fragment {

    private EditText edtTxtAddress, edtTxtZipCode, edtTxtPhoneNumber, edtTxtEmail;
    private Button btnBack, btnNext;
    private RelativeLayout parent, addressRelLayout, emailRelLayout, phoneNumberRelLayout, zipCodeRelLayout;
    private NestedScrollView nestedScrollView;

    private Order incomingOrder;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_cart,container,false);

        initViews(view);

        Bundle bundle = getArguments();
        if(null != bundle){
            incomingOrder = bundle.getParcelable("order");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.out,R.anim.in)
                        .replace(R.id.frameLayout,new CartFirstFragment()).commit();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    passData();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please fill all of the fields")
                            .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.create().show();
                }
            }
        });

        initRelLayouts();

        return view;

    }

    private void passData(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("order",incomingOrder);
        CartThirdFragment cartThirdFragment = new CartThirdFragment();
        cartThirdFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.in,R.anim.out)
                .replace(R.id.frameLayout,cartThirdFragment).commit();
    }

    private boolean validateData(){
        if(edtTxtAddress.getText().toString().equals("")){
            return false;
        }
        if(edtTxtEmail.getText().toString().equals("")){
            return false;
        }
        if(edtTxtZipCode.getText().toString().equals("")){
            return false;
        }
        if(edtTxtPhoneNumber.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void initViews(View view){
        edtTxtAddress = (EditText) view.findViewById(R.id.edtTxtAddress);
        edtTxtZipCode = (EditText) view.findViewById(R.id.edtTxtZipCode);
        edtTxtPhoneNumber = (EditText) view.findViewById(R.id.edtTxtPhoneNumber);
        edtTxtEmail = (EditText) view.findViewById(R.id.edtTxtEmail);

        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        parent = (RelativeLayout) view.findViewById(R.id.parent);
        addressRelLayout = (RelativeLayout) view.findViewById(R.id.addressRelLayout);
        emailRelLayout = (RelativeLayout) view.findViewById(R.id.emailRelLayout);
        phoneNumberRelLayout = (RelativeLayout) view.findViewById(R.id.phoneNumberRelLayout);
        zipCodeRelLayout = (RelativeLayout) view.findViewById(R.id.zipCodeRelLayout);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.scrollView);

    }

    private void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initRelLayouts(){

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        addressRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        emailRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        phoneNumberRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        zipCodeRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                closeKeyboard();
            }
        });
    }
}
