package com.example.shoppingmall.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    private int groceryItemId;
    private String username;
    private String date;
    private String text;

    public Review(int groceryItemId, String username, String date, String text) {
        this.groceryItemId = groceryItemId;
        this.username = username;
        this.date = date;
        this.text = text;
    }


    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Review(Parcel in) {
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "groceryItemId=" + groceryItemId +
                ", username=" + username +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groceryItemId);
        dest.writeString(username);
        dest.writeString(date);
        dest.writeString(text);
    }
}
