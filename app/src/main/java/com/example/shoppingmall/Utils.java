package com.example.shoppingmall;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shoppingmall.Models.GroceryItem;
import com.example.shoppingmall.Models.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static int ID = 0;
    private static int ORDER_ID = 0;

    public static final String DATABASE_NAME = "fake_database";

    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static int getOrderId(){
        ORDER_ID++;
        return ORDER_ID;
    }

    public static int getID() {
        ID++;
        return ID;
    }

    public void addItemToCart(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer> cartItems = gson.fromJson(sharedPreferences.getString("cartItems",null),type);
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.add(id);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartItems",gson.toJson(cartItems));
        editor.commit();
    }

    public void updateTheRate(GroceryItem item,int newRate){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allItems",null),type);
        if(null != items){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: items){
                 if(i.getId() == item.getId()){
                     i.setRate(newRate);
                 }
                 newItems.add(i);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allItems",gson.toJson(newItems));
            editor.commit();
        }
    }

    public boolean Add_Review(Review review){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allItems",null),type);
        if(null != items){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem item: items){
                if(item.getId() == review.getGroceryItemId()){
                    ArrayList<Review> reviews = item.getReviews();
                    reviews.add(review);
                    item.setReviews(reviews);
                }

                newItems.add(item);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allItems",gson.toJson(newItems));
            editor.commit();
            return true;
        }
        return false;
    }

    public ArrayList<Review> getReviewForItem(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allItems",null),type);
        if(null != items){
            for(GroceryItem item: items){
                if(item.getId() == id){
                    return item.getReviews();
                }
            }
        }
        return null;
    }

    public void initDatabase(){

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> possibleItems = gson.fromJson(sharedPreferences.getString("allItems",""),type);
        if(null == possibleItems ){
            initAllItems();
        }
    }

    public ArrayList<GroceryItem> getAllItems(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems",null),type);
        return allItems;
    }

    private void initAllItems(){

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<GroceryItem> allItems = new ArrayList<>();

        GroceryItem tropicana = new GroceryItem("Tropicana","Orange Juice",
                "https://images-na.ssl-images-amazon.com/images/I/71wMrB2fMBL._SL1500_.jpg","drinks",15,50);

        allItems.add(new GroceryItem("Cheese","Best Cheese possible",
                "https://www.koan.co.ke/wp-content/uploads/2019/08/cheese-powders-1035957-1.jpg","food",20,15));

        allItems.add(new GroceryItem("Cucumber","It is Fresh",
                "https://cdn.mos.cms.futurecdn.net/EBEXFvqez44hySrWqNs3CZ-320-80.jpg","vegetables",10,5));

        allItems.add(new GroceryItem("Coca cola","tasty beverage",
                "https://images-na.ssl-images-amazon.com/images/I/5156FefjlqL._SX425_.jpg","drinks",40,30));

        allItems.add(new GroceryItem("Tomato","Red tomatoes",
                "https://az836796.vo.msecnd.net/media/image/product/en/large/0000000004664.jpg","vegetables",20,3));

        allItems.add(new GroceryItem("Maggi","Makes in 2 minutes",
                "https://images-na.ssl-images-amazon.com/images/I/81JI5O0qB5L._SL1500_.jpg","food",50,15));

        allItems.add(new GroceryItem("Tresemme Shampoo","Strong and Smooth hair",
                "https://images-na.ssl-images-amazon.com/images/I/61kKwS8ELSL._SY355_.jpg","hygiene",30,60));

        allItems.add(new GroceryItem("Fogg Perfume","Lasts long and smells strong",
                "https://images-na.ssl-images-amazon.com/images/I/81M3kUWHZEL._SY450_.jpg","hygiene",40,100));

        allItems.add(new GroceryItem("Lays","Cream and Onion",
                "https://images-na.ssl-images-amazon.com/images/I/51rmheif4sL.jpg","food",20,10));

        allItems.add(tropicana);

        String finalString = gson.toJson(allItems);
        editor.putString("allItems",finalString);
        editor.commit();
    }

    public ArrayList<GroceryItem> searchItem(String text){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems",null),type);

        ArrayList<GroceryItem> searchItems = new ArrayList<>();
        if(null != allItems){
            for (GroceryItem item: allItems){
                if(item.getName().equalsIgnoreCase(text)){
                    searchItems.add(item);
                }
                String []splittedString = item.getName().split(" ");
                for (int i=0 ;i<splittedString.length; i++){
                    if (splittedString[i].equalsIgnoreCase(text)){

                        boolean doesExist = false;
                        for (GroceryItem s: searchItems){
                            if(s.equals(item)){
                                doesExist = true;
                            }
                        }

                        if (!doesExist){
                            searchItems.add(item);
                        }
                    }
                }
            }
        }
        return searchItems;
    }

    public ArrayList<String> getCategories(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<String> categories = new ArrayList<>();

        if(null!= allItems){
            for (int i=0; i<allItems.size(); i++){
                if(categories.size()<3){
                    boolean doesExist = false;
                    for(String s:categories){
                        if(allItems.get(i).getCategory().equals(s)){
                            doesExist = true;
                        }
                    }
                    if (!doesExist){
                        categories.add(allItems.get(i).getCategory());
                    }
                }
            }
        }
        return categories;
    }

    public ArrayList<String> getAllCategories(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<String> categories = new ArrayList<>();

        if(null!= allItems){
            for (int i=0; i<allItems.size(); i++){
                    boolean doesExist = false;
                    for(String s:categories){
                        if(allItems.get(i).getCategory().equals(s)){
                            doesExist = true;
                        }
                    }
                    if (!doesExist){
                        categories.add(allItems.get(i).getCategory());
                    }
            }
        }
        return categories;
    }

    public ArrayList<GroceryItem> getItemByCategory(String category){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems",null),type);

        ArrayList<GroceryItem> newItems = new ArrayList<>();
        if(null != allItems){
            for(GroceryItem item: allItems){
                if(item.getCategory().equals(category)){
                    newItems.add(item);
                }
            }
        }
        return newItems;
    }

    public ArrayList<Integer> getCartItems(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer> cartItems = gson.fromJson(sharedPreferences.getString("cartItems",null),type);
        return cartItems;
    }

    public ArrayList<GroceryItem> getItemsById (ArrayList<Integer> ids){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems",null),type);

        ArrayList<GroceryItem> resultItems = new ArrayList<>();

        for (int id:ids){
            if(null != allItems){
                for (GroceryItem item: allItems){
                    if (item.getId() == id){
                        resultItems.add(item);
                    }
                }
            }
        }
        return resultItems;
    }

    public ArrayList<Integer> deleteCartItem(GroceryItem item) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> cartItems = gson.fromJson(sharedPreferences.getString("cartItems", null), type);

        ArrayList<Integer> newItems = new ArrayList<>();

        if (null != cartItems) {
            for (int i: cartItems) {
                if (item.getId() != i) {
                    newItems.add(i);
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cartItems", gson.toJson(newItems));
            editor.commit();
        }

        return newItems;
    }

    public void removeCartItems () {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("cartItems");
        editor.apply();
    }

    public void addPopularityPoint(ArrayList<Integer> items){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);

        ArrayList<GroceryItem> newItems = new ArrayList<>();
        for (GroceryItem i: allItems) {
            boolean doesExist = false;
            for (int j: items) {
                if (i.getId() == j) {
                    doesExist = true;
                }
            }
            if (doesExist) {
                i.setPopularityPoint(i.getPopularityPoint()+1);
            }
            newItems.add(i);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("allItems", gson.toJson(newItems));
        editor.apply();
    }

    public void increaseUserPoint(GroceryItem item,int points){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: allItems) {
                if (i.getId() == item.getId()) {
                    i.setUserPoint(i.getUserPoint()+points);
                }
                newItems.add(i);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allItems", gson.toJson(newItems));
            editor.commit();
        }
    }

    public void decreaseAvailableAmount(GroceryItem item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>() {
        }.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allItems", null), type);

        if(allItems != null){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems){
                if(i.getId() == item.getId()){
                    i.setAvailableAmount(i.getAvailableAmount() - 1);
                }
                newItems.add(i);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allItems", gson.toJson(newItems));
            editor.commit();
        }
    }
}
