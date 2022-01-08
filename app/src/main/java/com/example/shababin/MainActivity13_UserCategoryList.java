package com.example.shababin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;

public class MainActivity13_UserCategoryList extends AppCompatActivity {

    public static String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity13_user_category_list);
        FirebaseApp.initializeApp(getApplicationContext());

    }

    public void MoveToOthers(View view) {
        selectedCategory="Others";
        Intent movet=new Intent(getApplicationContext(),MainActivity14_UserItemList.class);
        startActivity(movet);
    }

    public void MoveToAccess(View view) {
        selectedCategory="Accessocies";
        Intent movet=new Intent(getApplicationContext(),MainActivity14_UserItemList.class);
        startActivity(movet);
    }

    public void MoveToClothes(View view) {
       selectedCategory="Clothes";
        Intent movet=new Intent(getApplicationContext(),MainActivity14_UserItemList.class);
        startActivity(movet);
    }
}